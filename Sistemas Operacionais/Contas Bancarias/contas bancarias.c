#define _GNU_SOURCE
#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <stdlib.h>

#define MAX_TRANSACOES 100 // número máximo de transações simultaneas

struct c {
  int saldo;
};
typedef struct c conta;

conta from, to;
int valor1, valor2;

sem_t mutex;

// transferencia de from pra to
void *transferencia1(void *arg) {

  sem_wait(&mutex);

  printf("Transferindo %d da conta FROM para a conta TO\n", valor1);

  if (from.saldo >= valor1) {
    from.saldo -= valor1;
    to.saldo += valor1;
  } else {
    printf("Erro! Saldo insuficiente da conta FROM!\n\n");
    sem_post(&mutex);
    return NULL;
  }

  printf("Transferência concluída com sucesso!\n");
  printf("Saldo de FROM: %d\n", from.saldo);
  printf("Saldo de TO: %d\n\n", to.saldo);

  sem_post(&mutex);

  return NULL;
}

// transferencia de to pra from
void *transferencia2(void *arg) {

  sem_wait(&mutex);

  printf("Transferindo %d da conta TO para a conta FROM\n", valor2);

  if (to.saldo >= valor2) {
    to.saldo -= valor2;
    from.saldo += valor2;
  } else {
    printf("Erro! Saldo insuficiente da conta TO!\n\n");
    sem_post(&mutex);
    return NULL;
  }

  printf("Transferência concluída com sucesso!\n");
  printf("Saldo de TO: %d\n", to.saldo);
  printf("Saldo de FROM: %d\n\n", from.saldo);

  sem_post(&mutex);

  return NULL;
}

int main() {

  printf("Programa das transferências iniciado!\n\n");

  pthread_t threads[MAX_TRANSACOES]; // vetor de threads

  sem_init(&mutex, 0, 1); // inicialização do semáforo

  from.saldo = 100;
  to.saldo = 100;
  valor1 = 100; // valor que será transefido de from pra to
  valor2 = 100; // valor que será transefido de to pra from

  printf("Saldo inicial da conta FROM: %d\n", from.saldo);
  printf("Saldo inicial da conta TO: %d\n\n", to.saldo);

  int i;

  // criação das threads
  for (i = 0; i < MAX_TRANSACOES / 2; i++) {
    pthread_create(&(threads[i]), NULL, transferencia1, NULL);
    pthread_create(&(threads[i + (MAX_TRANSACOES / 2)]), NULL, transferencia2, NULL);
  }

  for (i = 0; i < MAX_TRANSACOES; i++) {
    pthread_join(threads[i], NULL);
  }

  sem_destroy(&mutex); // finalização do semáforo

  printf("Transferências concluídas e memória liberada.\n\nSaldo final da conta TO: %d\nSaldo final da conta FROM: %d\n\nPrograma finalizado!\n", to.saldo, from.saldo);

  return 0;
}