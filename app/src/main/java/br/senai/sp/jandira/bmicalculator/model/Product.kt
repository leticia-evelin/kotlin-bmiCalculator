package br.senai.sp.jandira.bmicalculator.model

class Product {

    //private int id; -> java
    var id: Int = 0
    var productName: String = ""
    var productPrice = 0.0

//    public double getProductPrice(){
//        -> java
//    }

    fun getPrice(): Double {
        return 0.0
    }
    // ou
    fun getPrice2() = 0.0

    // public void getTest(){
// -> java
// }
    fun getTest(){

    }
}