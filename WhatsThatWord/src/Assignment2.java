import javax.swing.JOptionPane;
public class Assignment2
{
    public static void main (String [] args)
    {
	
	/**Use assignement one
		-if, else statement for determing what the rate is, based on the amount of money invested
		-switch statement for checking or saving
		-implement JOptionPane
		
		-need accountType variable - int or string? */
		
	
	double amount, rate = 0, interest;
		int year, accountInt; 
		String accountType = "default", accountString, amountString, yearString;
		
		JOptionPane.showMessageDialog(null, "Is this a checking account or savings account?");
		accountString = JOptionPane.showInputDialog("Press 1 for checking account.\n Press 2 for savings account");
		accountInt = Integer.parseInt(accountString);
		
		amountString = JOptionPane.showInputDialog("Enter the amount of money to invest: ");
		amount = Integer.parseInt(amountString);
		
		yearString = JOptionPane.showInputDialog("Enter the number of years to invest: ");
		year = Integer.parseInt(yearString);
		
		switch (accountInt){
			case 1: 
				accountType = "checking";
				
				if (amount <= 1000 && amount >= 1){
					rate = .01;
				}
				else if (amount <= 2000) {
					rate = .02;
				}
				else if (amount <= 5000) {
					rate = .03;
				}		
				else if (amount <= 10000) {
					rate = .035;
				}
				else if (amount <= 25000) {
					rate = .04;
				}
				else {
					rate = .05;
				}
				break;
			case 2:
				accountType = "savings";
				
				if (amount <= 1000 && amount >= 1){
					rate = .02;
				}
				else if (amount <= 2000) {
					rate = .03;
				}
				else if (amount <= 5000) {
					rate = .04;
				}		
				else if (amount <= 10000) {
					rate = .045;
				}
				else if (amount <= 25000) {
					rate = .05;
				}
				else {
					rate = .06;
				}
				break;
			default: 
			JOptionPane.showMessageDialog(null, "Error, invalid account type. Exiting program");
			System.exit(0);
				
		}
		
		/*System.out.println("Enter the desired rate of return (as a decimal): ");
		rate = input.nextDouble(); */
		
		interest = (amount * rate * year);
		
		JOptionPane.showMessageDialog(null, "If you invest $" + amount + "into a " + accountType + "account, your interest rate will be " + (rate * 100) + "%.");
		JOptionPane.showMessageDialog(null, "Your amount earned after " + year + " year(s) will be $" + interest + "and your total amount will be $" + (interest + amount) + ".");
		System.exit(0);
		
		/*
		System.out.println("If you invest $" + amount + " at a rate of " + (rate * 100) + "%, your amount earned after " + year + " year(s) will be $" + interest);
		System.out.println("and your total amount will be $" + (interest + amount) + ".");
		*/
	
	}
	
}