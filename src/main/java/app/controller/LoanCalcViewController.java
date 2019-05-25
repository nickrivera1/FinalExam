package app.controller;

import app.StudentCalc;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import org.apache.poi.ss.formula.functions.*;

public class LoanCalcViewController implements Initializable   {


	
	private StudentCalc SC = null;
	
	@FXML
	private TableView<Helper> table;
	
	@FXML
	private TextField LoanAmount;
	
	@FXML
	private TextField InterestRate;
	
	@FXML
	private TextField NumOfYears;
	
	@FXML
	private TextField AdditionalPayment;
	
	@FXML
	private Label lblTotalPayemnts;
	
	@FXML
	private Label lblTotalInterest;
	
	@FXML
	private DatePicker PaymentStartDate;
	
	@FXML
	private TableColumn<Helper, Integer> PaymentNumColumn;
	
	@FXML
	private TableColumn<Helper, LocalDate> DueDateColumn;
	
	@FXML
	private TableColumn<Helper, Double> PaymentColumn;
	
	@FXML
	private TableColumn<Helper, Double> AddPaymentColumn;
	
	@FXML
	private TableColumn<Helper, Double> InterestColumn;
	
	@FXML
	private TableColumn<Helper, Double> PrincipleColumn;
	
	@FXML
	private TableColumn<Helper, Double> BalanceColumn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		PaymentNumColumn.setCellValueFactory(new PropertyValueFactory<Helper, Integer>("Payment Number"));
		
		DueDateColumn.setCellValueFactory(new PropertyValueFactory<Helper, LocalDate>("Due Date"));
		
		PaymentColumn.setCellValueFactory(new PropertyValueFactory<Helper, Double>("Payment"));
		
		AddPaymentColumn.setCellValueFactory(new PropertyValueFactory<Helper, Double>("Additonal Payment"));
		
		InterestColumn.setCellValueFactory(new PropertyValueFactory<Helper, Double>("Interest"));
		
		PrincipleColumn.setCellValueFactory(new PropertyValueFactory<Helper, Double>("Principle"));
		
		BalanceColumn.setCellValueFactory(new PropertyValueFactory<Helper, Double>("Balance"));
		
		
	}

	public void setMainApp(StudentCalc sc) {
		this.setSC(sc);
	}
	
	/**
	 * btnCalcLoan - Fire this event when the button clicks
	 * 
	 * @version 1.0
	 * @param event
	 */
	@FXML
	private void btnCalcLoan(ActionEvent event) {

		System.out.println("Payment Number, Due Date, Payment, Additional Payment, Interest, Principle, Balance");
		ObservableList<Helper> Helpers = FXCollections.observableArrayList();

		
		double DoubleNumOfYears = Double.parseDouble(NumOfYears.getText());
		double DoubleLoanAmount = Double.parseDouble(LoanAmount.getText());
		double DoubleInterestRate = Double.parseDouble(InterestRate.getText()) / 100;
		double DoubleAddPayment = Double.parseDouble(AdditionalPayment.getText());
		
		boolean stop = false;
		LocalDate localDate = PaymentStartDate.getValue().minusMonths(1);
		
		
		
		double TotalPayments = 0;
		double TotalInterest = 0;
		
		double interest;
		double principle;
		
		double balance = DoubleLoanAmount;
		double PMT = Math.abs(FinanceLib.pmt(DoubleInterestRate / 12, DoubleNumOfYears * 12, DoubleLoanAmount, 0, false));
		double Payment = PMT;

		for (int x = 1; x <= (DoubleNumOfYears * 12); x++) 
		{
			interest = DoubleInterestRate / 12 * balance;
			principle = PMT - interest + DoubleAddPayment;
			
			if (balance - principle < 0) 
			{
				principle = balance;
				stop = true;
			}
			
			balance -= principle;
			localDate = localDate.plusMonths(1);
			TotalInterest += interest;

			Helpers.add(new Helper(x, Math.round(Payment * 100.0) / 100.0, DoubleAddPayment, Math.round(interest * 100.0) / 100.0, Math.round(principle * 100.0) / 100.0, Math.round(balance * 100.0) / 100.0, localDate));
			
			System.out.println(x + ",\t" + localDate + ", " + Math.round(Payment * 100.0) / 100.0 + ", " + DoubleAddPayment + ", " + Math.round(interest * 100.0) / 100.0 + ", " + Math.round(principle * 100.0) / 100.0 + ", "+ Math.round(balance * 100.0) / 100.0);
			
			if (stop) 
			{
				DoubleNumOfYears = x / 12;
				break;
			}
		}
		
		table.setItems(Helpers);

		TotalPayments = TotalInterest + DoubleLoanAmount;
		lblTotalPayemnts.setText(String.format("%.02f", TotalPayments));
		lblTotalInterest.setText(String.format("%.02f", TotalInterest));

		System.out.println("Loan Amount: " + DoubleLoanAmount);
		System.out.println("Interest Rate: " + DoubleInterestRate);
		System.out.println("Term: " + DoubleNumOfYears);
		System.out.println("Additional Payment: " + DoubleAddPayment);
		System.out.println("Total Payments: " + TotalPayments);
		System.out.println("Total Interest: " + TotalInterest);
		System.out.println("Final Payment Date: " + localDate);
	
	}
	

	public StudentCalc getSC() {
		return SC;
	}

	public void setSC(StudentCalc sc) {
		SC = sc;
	}
}
