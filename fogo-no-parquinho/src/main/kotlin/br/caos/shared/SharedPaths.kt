package br.caos.shared

class SharedPaths {
    companion object{
        val CONNECTION_STRING = "jdbc:mariadb://192.168.0.200:3306/fogo_no_parquinho?user=fogo&password=parquinho"

        // Variáveis usadas para gerar e validar Token
        // (OBS: PELA MOR DE DEUS JAMAIS FAÇA ISSO SE FOR USAR APLICAÇÃO FORA DE APRENDIZADO, DADOS SECRETOS NÃO DEVEM FICAR AQUI!!!)
        val JWT_SECRET = "let_them_burn"
        val JWT_AUD = "fogo_no_parquinho.com/login"
        val JWT_ISS = "fogo_no_parquinho.com/"
    }
}