// SEQUENCIAL

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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
    bucket *buckets = NULL, *b = NULL;
    long int *returns = NULL;

    returns = (long int *) malloc(sizeof(long int) * size);
    buckets = (bucket *) malloc(sizeof(bucket) * nbuckets);

    for (i = 0; i < nbuckets; i++) {
        buckets[i].data = returns + i * size / nbuckets;
        buckets[i].length = length;
        buckets[i].total = 0;
    }

    for (i = 0; i < size; i++) {
        b = &buckets[*(a + i * length) - offset];
        b->data[b->total++] = i;
    }

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

void print(char *strings, long int *index, long int n, int len){

   for (int i = 0; i < n; i++){
      printf("%d -> %s\n", i, strings + (index[i] * len));
   }
   
}

void save(char *strings, long int *index, long int n, int len) {
    
    for (long int i = 0; i < n; i++) {
        printf("%s\n", strings + (index[i] * len));
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