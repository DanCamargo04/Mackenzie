#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <omp.h> // biblioteca da OpenMP

typedef struct {
    
    long int *data;
    int length;
    long int total;
    
} bucket;

void sort(char *a, bucket *bucket) {
    
    int j, i, length;
    long int key;
    length = bucket->length;

    for (j = 1; j < bucket->total; j++) {
        key = bucket->data[j];
        i = j - 1;
        while (i >= 0 && strcmp(a + bucket->data[i] * length, a + key * length) > 0) {
            bucket->data[i + 1] = bucket->data[i];
            i--;
        }
        bucket->data[i + 1] = key;
    }
}

long int* bucket_sort(char *a, int length, long int size, int offset, int nbuckets) {
    
    long int i;
    bucket *buckets = NULL;
    long int *returns = NULL;

    returns = (long int *) malloc(sizeof(long int) * size);
    buckets = (bucket *) malloc(sizeof(bucket) * nbuckets);

    for (i = 0; i < nbuckets; i++) {
        buckets[i].data = returns + i * size / nbuckets;
        buckets[i].length = length;
        buckets[i].total = 0;
    }

    #pragma omp parallel
    {
        int tid = omp_get_thread_num();
        int nthreads = omp_get_num_threads();

        bucket *local_buckets = (bucket *) malloc(sizeof(bucket) * nbuckets);
        for (int j = 0; j < nbuckets; j++) {
            local_buckets[j].data = (long int *) malloc(sizeof(long int) * (size / nthreads));
            local_buckets[j].length = length;
            local_buckets[j].total = 0;
        }

        #pragma omp for // distribuição das strings nos baldes em cada core
        for (long int i = 0; i < size; i++) {
            bucket *b = &local_buckets[*(a + i * length) - offset];
            b->data[b->total++] = i;
        }

        #pragma omp critical
        {
            for (int j = 0; j < nbuckets; j++) {
                bucket *global_b = &buckets[j];
                for (long int k = 0; k < local_buckets[j].total; k++) {
                    global_b->data[global_b->total++] = local_buckets[j].data[k];
                }
            }
        }

        for (int j = 0; j < nbuckets; j++) {
            free(local_buckets[j].data);
        }
        free(local_buckets);
    }

    #pragma omp parallel for // cada core faz a ordenação
    for (i = 0; i < nbuckets; i++) {
        sort(a, &buckets[i]);
    }

    return returns;
}

// gerar dados de 250000 até 000000 automaticamente
void load(char **vString, long int *N, int *l, int *os, int *nb) {
    
    char *strings = NULL;
    int len = 7; 
    int offset = 48; 
    int nbucket = 10;
    long int n = 250000; 

    strings = (char *) malloc(n * len);

    for (long int i = 0; i < n; i++) {
        snprintf(strings + (i * len), len, "%06ld", n - i - 1);
    }

    *vString = strings;
    *N = n;
    *l = len;
    *os = offset;
    *nb = nbucket;
}

void save(char *strings, long int *index, long int n, int len) {
    
    for (long int i = 0; i < n; i++) {
        printf("%s\n", strings + (index[i] * len));
    }

    
}

void print(char *strings, long int *index, long int n, int len){

   for (int i = 0; i < n; i++){
      printf("%d -> %s\n", i, strings + (index[i] * len));
   }
   
}

int main(int ac, char **av) {
    
    char *strings = NULL;
    long int N = 0;
    long int *index = NULL;
    int len = 0, offset, nbuckets;

    load(&strings, &N, &len, &offset, &nbuckets);

    index = bucket_sort(strings, len, N, offset, nbuckets);

    save(strings, index, N, len);

    if (strings != NULL)
        free(strings);
    if (index != NULL)
        free(index);

    return EXIT_SUCCESS;
}