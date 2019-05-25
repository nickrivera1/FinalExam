package pkgUT;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.apache.poi.ss.formula.functions.*;
import org.junit.Test;
import app.controller.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TestPMT {

	@Test
	public void test() 
	{
		double PMT;
		
		double rate = 0.2 / 12;
		double n = 25 * 12;
		double principle = 20000;
		double f = 0;
		boolean test = false;
		
		
		PMT = Math.abs(FinanceLib.pmt(rate, n, principle, f, test));

		double PMTExpected = 335.69;

		assertEquals(PMTExpected, PMT, 0.01);

	}

	public static double [] buttonAction(double doubleLoanAmount, double doubleInterestRate, int doubleNumOfYears, LocalDate localDate, double doubleAddPayment)
	{
		ObservableList<Helper> helpers = FXCollections.observableArrayList();

		double PMT = Math.abs(FinanceLib.pmt(doubleInterestRate / 12, doubleNumOfYears * 12, doubleLoanAmount, 0, false));
		
		double TotalPayments = 0;
		double TotalInterest = 0;
		
		double Payment = PMT;
		double principle;
		double interest;
		
		double balance = doubleLoanAmount;
		boolean stop = false;

		for (int x = 1; x <= (doubleNumOfYears * 12); x ++) 
		{
			interest = doubleInterestRate / 12 * balance;
			principle = PMT - interest + doubleAddPayment;
			
			if (balance - principle < 0) 
			{
				principle = balance;
				stop = true;
			}
			
			balance -= principle;
			localDate = localDate.plusMonths(1);
			TotalInterest += interest;

			helpers.add(new Helper(x, Math.round(Payment * 100.0) / 100.0, doubleAddPayment, Math.round(interest * 100.0) / 100.0, Math.round(principle * 100.0) / 100.0,Math.round(balance * 100.0) / 100.0, localDate));
			
			if (stop) 
			{
				doubleNumOfYears = x / 12;
				break;
			}
		}

		TotalPayments = TotalInterest + doubleLoanAmount;

		double [] answer = {TotalPayments, TotalInterest, doubleNumOfYears};
		return answer;
	}

	@Test
	public void buttonActionTest() {
		double[] mylist = buttonAction(150000, 0.5, 30, LocalDate.of(2018, 5, 1), 100);
		assertEquals(mylist[0], 645732.52, 0.01);
		assertEquals(mylist[1], 495732.52, 0.01);
		assertEquals(mylist[2], 8.0, 0.01);

	}

}

 

