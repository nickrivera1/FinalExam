package app.controller;

import java.time.LocalDate;

public class Helper {

	private Integer PaymentNum;
	private Double Payment;
	private Double AddPayment;
	private Double Principle;
	private Double Balance;
	private Double Interest;
	private LocalDate DueDate;
	
	public Helper (Integer paynum, Double pay, Double addpay, Double prince, Double bal, Double interest,LocalDate due)
	{
		PaymentNum = new Integer(paynum);
		Payment = new Double(pay);
		AddPayment = new Double(addpay);
		Principle = new Double(prince);
		Balance = new Double(bal);
		Interest = new Double(interest);
		DueDate = due;
	}
	
	public Integer getPaymentNum()
	{
		return PaymentNum;
		
	}
	
	public void setPaymentNum(Integer paynum)
	{
		PaymentNum = paynum;
	}
	
	public Double getPayment()
	{
		return Payment;
		
	}
	
	public void setPayment(Double pay)
	{
		Payment = pay;
	}
	
	public Double getAddPayment()
	{
		return AddPayment;
		
	}
	
	public void setAddPayment(Double addpay)
	{
		AddPayment = addpay;
	}
	
	public Double getPrinciple()
	{
		return Principle;
		
	}
	
	public void setPrinciple(Double prince)
	{
		Principle = prince;
	}
	
	public Double getBalance()
	{
		return Balance;
		
	}
	
	public void setBalance(Double bal)
	{
		Balance = bal;
	}
	
	public Double getInterest()
	{
		return Interest;
		
	}
	
	public void setInterest(Double interest)
	{
		Interest = interest;
	}
	
	public LocalDate getDueDate()
	{
		return DueDate;
	}
	
	public void setDueDate(LocalDate due)
	{
		DueDate = due;
	}
	
}
