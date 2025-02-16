from sqlalchemy import create_engine
import pandas as pd
import matplotlib.pyplot as plt

def showMenu():
    print("\n---------------- Menu ----------------\nOpção 1: Mostrar jogos mais vendidos\nOpção 2: Visualizar uma query\nOpção 3: Sair\n---------------------------------------")

print("\nIniciando conexão...")

engine = create_engine("mysql+mysqlconnector://root:mysqlroot!@localhost/projeto_game")

gamesSoldQuery = """
        SELECT Jogo.nome_jogo, COUNT(Compra_Jogo.id_jogo) AS quantidade_compras
        FROM Jogo
        JOIN Compra_Jogo ON Jogo.id_jogo = Compra_Jogo.id_jogo 
        GROUP BY Jogo.nome_jogo
        ORDER BY quantidade_compras DESC
        """

df = pd.read_sql(gamesSoldQuery, engine)

fig, ax = plt.subplots(figsize=(10, 6)) 
df.plot(kind='bar', x='nome_jogo', y='quantidade_compras', color='orange', legend=False, ax=ax)
ax.set_title('Jogos Mais Comprados')
ax.set_xlabel('Nome do Jogo')
ax.set_ylabel('Quantidade de Compras')
plt.tight_layout()  

option = 0

while option != 3:
    
    showMenu()
    option = int(input("Opção: "))

    if option == 1:
        plt.show()
    elif option == 2:
        user_query = input("Insira a query desejada: ")
        df2 = pd.read_sql(user_query, engine)
        print("\n{}\n".format(df2))

print("\nConexão ao MySQL foi encerrada!")
    
print("\nPrograma encerrado!\n")