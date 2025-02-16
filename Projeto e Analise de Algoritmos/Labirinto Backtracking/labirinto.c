/*
Daniel Rubio Camargo - RA: 10408823
João Pedro Mascaro Baccelli - RA: 10224004
*/

#include <stdio.h>
#include <stdlib.h>

char **maze;
int lines, columns;
char wall = '#', empty = '.', walked = 'o', entrance = 'E', ending = 'S';

int inMaze(int x, int y) {
  return ((x >= 0 && x < lines) && (y >= 0 && y < columns));
}

// backtracking
int findPath(int x, int y) {
  
  if (maze[x][y] == ending) return 1;

  maze[x][y] = walked;

  int foundPath = 0;

  if (inMaze(x - 1, y) && maze[x - 1][y] != wall && maze[x - 1][y] != walked)
    foundPath = findPath(x - 1, y);

  if (!foundPath && inMaze(x + 1, y) && maze[x + 1][y] != wall && maze[x + 1][y] != walked)
    foundPath = findPath(x + 1, y);

  if (!foundPath && inMaze(x, y - 1) && maze[x][y - 1] != wall && maze[x][y - 1] != walked)
    foundPath = findPath(x, y - 1);

  if (!foundPath && inMaze(x, y + 1) && maze[x][y + 1] != wall && maze[x][y + 1] != walked)
    foundPath = findPath(x, y + 1);
  
  if (!foundPath) maze[x][y] = empty;

  return foundPath;
}

void startPath(){

  int xInitial = 0, yInitial = 0, i, j;

  // encontrar onde começa o labirinto
  for (i = 0; i < lines; i++) {
    for (j = 0; j < columns; j++) {
      if (maze[i][j] == entrance) {
        if (i > 0) {
          xInitial = i;
          yInitial = j + 1;
        } else {
          xInitial = i + 1;
          yInitial = j;
        }
        // começa o percurso com os valores de inicio encontrados
        findPath(xInitial, yInitial);
        break;
      }
    }
  }

}

int main() {

  printf("Programa iniciado!\n\n");

  FILE *inputFile = fopen("input.txt", "r");
  FILE *outputFile = fopen("output.txt", "w");

  if (inputFile != NULL && outputFile != NULL) {
    printf("Arquivos criados com sucesso\n");
  }

  // receber os dois valore da primeira linha e atribuir nas variaveis lines e columns como inteiros
  fscanf(inputFile, "%d %d", &lines, &columns);

  maze = (char **)malloc(lines * sizeof(char *));
  for (int i = 0; i < lines; i++) {
    maze[i] = (char *)malloc(columns * sizeof(char));
    fscanf(inputFile, "%s", maze[i]);
  }

  startPath();
  
  printf("Percurso finalizado com sucesso\n");

  for (int i = 0; i < lines; i++) {
    fprintf(outputFile, "%s\n", maze[i]);
  }

  free(maze);

  printf("Memória da matriz do labirinto liberada com sucesso\nArquivo output.txt "
         "preenchido com sucesso\n");

  fclose(inputFile);
  fclose(outputFile);

  printf("Arquivos fechados com sucesso\n\nPrograma finalizado!\n");

  return 0;
}
