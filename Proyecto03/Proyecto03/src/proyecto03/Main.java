package proyecto03;

public class Main {
    
    private static String encriptado ;
    private static String desencriptado;

    public static void main(String[] args) {
        
        Aes prueba = new Aes();
        //Texto que se va a cifrar
        encriptado = prueba.getAES("Hola Mundo");
        System.out.print("Llave después del cifrado: ");
        System.out.println(Aes.llave);
        
        System.out.println("Texto encriptado: " + encriptado);
        System.out.println("");
        desencriptado = prueba.getAESDecrypt(encriptado);
        System.out.println("Texto desencriptado: " + desencriptado);
          
    }
   
}
