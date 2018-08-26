# Compilador LA
Primeiro trabalho da disciplina de Construção de Compiladores 2

## 1. Primeiros passos
- Instalar o plug-in do ANTLR no Intellij;
- Marcar a pasta 'gen/' como **source** (para a identificação de classes Java.

## 2. Como gerar classes do ANTLR
### 2.1 Intellij
- Clicar no botão direito no código .g4;
- Selecionar "Generate ANTLR Recognizer";
- Uma pasta `gen/` será gerada.

## 3. Testar regras manualmente
- Clicar no botão direito na regra desejada do arquivo .g4;
- Selecionar "Test rule";
- Um display gráfico com árvore de derivação aparecerá


## 4. Geração de arquivo JAR
* 4.1. Em **File > Project Structure > Artifacts** clicar em **`+` > JAR**  acima;
* 4.2. Selecionar a classe [`Principal`](https://github.com/joaobarbirato/cc2-compilador-LA/blob/joao/src/main/java/T1/Principal.java);

A esse ponto, um artefato para a classe `Principal` é criado.

* 4.3. Em **Build > Build Artifacts** selecione o projeto e clique em **Action > Build**
* 4.4. O arquivo está na pasta gerada `target/`. Aperte `CTRL + F12` e digite em seu terminal:
```bash
java -jar target/cc2-compilador-LA-1.0-SNAPSHOT.jar [<parametros>]
```
