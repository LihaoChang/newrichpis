package com.newRich.backRun;

import java.math.BigDecimal;

public class Test03 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String ff = "-123K";
		System.out.println("ff:" + ff.substring((ff.length()-1), ff.length()));
		System.out.println("ff:" + ff.substring(0,(ff.length()-1)));
		Double dd = new Double("-1234567893698.1236548");
		System.out.println("dd:" + dd/100000000);
		// TODO Auto-generated method stub
		// Compound interest 複利計算
		BigDecimal principal = new BigDecimal("1000000");
		BigDecimal interestRate = new BigDecimal("0.05");
		int runTime = 1;
		BigDecimal total = principal;
		for (int i = 0; i < runTime; i++) {
			System.out.println("money:{" + total.multiply(interestRate).floatValue() + "]");
			total = total.multiply(interestRate.add(new BigDecimal("1")));
		}
		System.out.println("total:{" + total.floatValue() + "]");
	}

}
