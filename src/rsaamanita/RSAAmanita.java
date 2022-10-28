/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsaamanita;

import java.math.BigInteger;
import java.util.*;
import java.io.*;
/**
 *
 * @author alumno
 */
public class RSAAmanita {

    int tamprimo;
    BigInteger p, q, n, fi, e, d;
    
    public RSAAmanita(int tamprimo){
        this.tamprimo=tamprimo;
    }
    
    //metodo para generar numeros primos de p y q
    
    public void genPrimes(){
        p=new BigInteger(tamprimo, 10,new Random());
        //el otro primo debe ser diferente
        do q = new BigInteger(tamprimo, 19, new Random());
        while(q.compareTo(p)==0);
    }
    
    public void genKey(){
        //n = p*q
        //fi = (p-1)(q-1)
        n = p.multiply(q);
        fi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        //e debe ser un coprimo talque 1 < e < fi(n)
        do e = new BigInteger(2*tamprimo, new Random());
        while(e.compareTo(fi)!=-1 || e.gcd(fi).compareTo(BigInteger.ONE)!=0);
        
        //d es el inverso multiplicativo de e de forma tal que d = e^1mod fi
        
        d = e.modInverse(fi);
    }
    
    public BigInteger[] cifrar(String mensajes){
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensajes.getBytes();
        
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        //recorro el arreglo 
        for(i=0; i<bigdigitos.length; i++){
            temp[0]=digitos[i];
            bigdigitos[i]=new BigInteger(temp);
        }
        
        BigInteger[] cifrado = new BigInteger[bigdigitos.length];
        
        for(i=0;i<bigdigitos.length;i++){
            cifrado[i] = bigdigitos[i].modPow(e, n);
        }
        
        return cifrado;
        
    }
    
    public String cifrar(BigInteger[] cifrado){
        int i;
        BigInteger[] descifrado = new BigInteger[cifrado.length];
        
        //descifrar Md = des ^ d * mod n
        
        //recorro el arreglo 
        for(i=0; i<descifrado.length; i++){
            descifrado[i] = cifrado[i].modPow(d, n);
        }
        
        char[] charArray = new char[descifrado.length];
        
        //genero mi cadena
        
        for(i=0; i<charArray.length;i++){
            charArray[i]=(char)descifrado[i].intValue();
        }
        
        return new String(charArray);
        
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
