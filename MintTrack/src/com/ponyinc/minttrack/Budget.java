package com.ponyinc.minttrack;

import android.content.Context;
import android.database.Cursor;

import com.ponyinc.minttrack.types.Accounts;
import com.ponyinc.minttrack.types.Categories;
import com.ponyinc.minttrack.types.Transactions;
/**	
*	Class represents the Budget object 
*   and contains methods for interacting with them.
*	The budget class is an interface class that contains methods 
*	that work with all database objects to provide correct functionality
*	@author Stephan Krach & Christopher Wilkins
*/
public class Budget implements Constants{
	private MintData MintLink;
	private Accounts accounts;
	private Transactions transactions;
	private Categories categories;
	
	
	/** Secondary Constructor
	*	@param ctx The context in which the object is being created
	*/
	public Budget(Context ctx) {
		MintLink = new MintData(ctx);
		// Just closing it till we figure out exactly what we want to do.
		MintLink.close();

		accounts = new Accounts(MintLink);
		transactions = new Transactions(MintLink);
		categories = new Categories(MintLink);
	}
	/** Method is a delegated version of addAccount.  This method should always be used instead of the Account Version directly.
	*	@param strName Name of the account being added
	*	@param value Initial balance of the account
	*	@param isActive Is account active
	*/
	public void addAccount(String strName, double value, boolean isActive) {
		accounts.addAccount(strName, value, isActive);
		
	}
	/** Method is a delegated version of getAllAccounts(). This method should always be used instead of the Account Version directly.
	*	@return A cursor containing all accounts.
	*/
	public Cursor getAllAccounts() {
		return accounts.getAllAccounts();
	}
	
	public Cursor getAccount(long acc_ID)
	{
		return accounts.getAccount(acc_ID);
	}
	/** Method is a delegated version of getActiveAccounts(). This method should always be used instead of the Account Version directly.
	*	@return A cursor containing only all the active accounts
	*/
	public Cursor getActiveAccounts() {
		return accounts.getActiveAccounts();
	}
	/** Method is a delegated version of DeactivateAccount(). This method should always be used instead of the Account Version directly.
	*	@param acc_id ID of the account to be deactivated
	*/
	public void DeactivateAccount(long acc_id) {
		accounts.DeactivateAccount(acc_id);
	}
	/** Method is a delegated version of EditAccountName(). This method should always be used instead of the Account Version directly.
	*	@param acc_id ID of account being modified
	*	@param strName New name to be applied to existing account
	*/
	public void EditAccountName(long acc_id, String strName) {
		accounts.EditAccountName(acc_id, strName);
	}
	/** Method is a delegated version of EditAccountTotal(). This method should always be used instead of the Account Version directly.
	*	@param acc_id ID of account to be modified
	*	@param total New total to be applied to existing account
	*/
	public void EditAccountTotal(long acc_id, double total) {
		accounts.EditAccountTotal(acc_id, total);
	}
	/** Method is a delegated version of ReactivateAccount(). This method should always be used instead of the Account Version directly.
	*	@param acc_id ID of existing account being reactivated
	*/
	public void ReactivateAccount(long acc_id) {
		accounts.ReactivateAccount(acc_id);
	}
	/** Method is a delegated version of ReactivateCategorys(). This method should always be used instead of the Categorys Version directly.
	*	@param acc_id ID of existing category being reactivated
	*/
	public void DeactivateCategory(long acc_id) {
		categories.DeactivateCategory(acc_id);
	}
	/** Method is a delegated version of DeactivateCategorys(). This method should always be used instead of the Categorys Version directly.
	*	@param acc_id ID of existing category being deactivated
	*/
	public void ReactivateCategory(long acc_id) {
		categories.ReactivateCategory(acc_id);
	}
	/** Method is a delegated version of getCategorys(). This method should always be used instead of the Categories Version directly.
	*	@return Cursor containg all existing categories
	*/
	public Cursor getAllCategorys() {
		return categories.getAllCategorys();
	}
	/** Method is a delegated version of getCategorys(). This method should always be used instead of the Categories Version directly.
	*	@return Cursor containing all active existing categories
	*/
	public Cursor getActiveCategorys() {
		return categories.getActiveCategorys();
	}
	/**
	 * 
	 * @param type 0 is for income and 1 is for expense
	 * @return Cursor of category of type income our expense.
	 */
	public Cursor getCategorys(int type) {
		return categories.getCategorys(type);
	}
	/** Method is a delegated version of getCategory(). This method should always be used instead of the Categories Version directly.
	*	@param intID ID of the category that you want returned in a query
	*	@return Cursor containing the cursor requested
	*/
	public Cursor getCategory(long intID) {
		return categories.getCategory(intID);
	}
	/** Method is a delegated version of addCategory(). This method should always be used instead of the Categories Version directly.
	*	@param strName Name of new category
	*	@param initalValue Initial balance of new category
	*	@param iType Type of category that it is
	*	@param isActive sets new category to active or inactive
	*/
	public void addCategory(String strName, double initalValue, int iType, boolean isActive) {
		categories.addCategory(strName, initalValue, iType, isActive);
	}
	/** Method is a delegated version of EditCategoryType(). This method should always be used instead of the Categories Version directly.
	*	@param iCatId ID of category being modified
	*	@param iType New type for the account
	*/
	public void EditCategoryType(long iCatId, int iType) {
		categories.EditCategoryType(iCatId, iType);
	}
	/** Method is a delegated version of getCategorys(). This method should always be used instead of the Categories Version directly.
	*	@param iCatID ID of category being modified
	*	@param strCatName New name for the category
	*/
	public void EditCategoryName(long iCatID, String strCatName) {
		categories.EditCategoryName(iCatID, strCatName);
	}
	/** Method is a delegated version of getCategorys(). This method should always be used instead of the Categories Version directly.
	*	@param iCatID ID of category being modified
	*	@param dblTotal New total for the category
	*/
	public void EditCategoryTotal(long iCatID, double dblTotal) {
		categories.updateCategory(iCatID, dblTotal);
	}
	/** Method is used to create a transfer transaction.  A transfer is any transaction that results in money being moved from one account to another.
	*	@param ToAccount_ID Account the transfer amount is going to
	*	@param FromAccount_ID Account the money is being deducted from
	*	@param Amount The value of the currency being moved from one account to another
	*	@param Note A string that contains information about the reason for the transaction
	*	@param Date Date the transaction is being created
	*	@param Category The category or reason that the transfer is occurring
	*/
	public boolean Transfer(String[] newInfo) {
		
		Cursor Account_To = accounts.getAccount(Long.parseLong(newInfo[TO]));
		Cursor Account_From = accounts.getAccount(Long.parseLong(newInfo[FROM]));
		
		Account_To.moveToNext();
		Account_From.moveToNext();
		
		double moneyInAccount = Account_From.getDouble(Account_From.getColumnIndex(ACCOUNT_TOTAL));
		if(moneyInAccount >= Double.parseDouble(newInfo[AMOUNT])){
			transactions.createTransfer(newInfo);
			EditAccountTotal(Long.parseLong(newInfo[FROM]), moneyInAccount - Double.parseDouble(newInfo[AMOUNT]));
			EditAccountTotal(Long.parseLong(newInfo[TO]), moneyInAccount + Double.parseDouble(newInfo[AMOUNT]));
			return true;
		}
		return false;
	}
	
	public boolean updateTransfer(long trans_ID, String[] oldInfo, String[] newInfo)
	{
		Cursor trans = transactions.getTransaction(trans_ID);
		Cursor acctTo = accounts.getAccount(Long.parseLong(oldInfo[TO]));
		Cursor acctFrom = accounts.getAccount(Long.parseLong(oldInfo[FROM]));
		
		trans.moveToFirst();
		acctTo.moveToFirst();
		acctFrom.moveToFirst();
		
		/*double oldAmount = trans.getDouble(trans.getColumnIndex(TRANSACTION_AMOUNT));
		long oldFromAccountId = acctFrom.getLong(acctFrom.getColumnIndex(_ID));
		long oldToAccountId = acctTo.getLong(acctTo.getColumnIndex(_ID));
		
		Cursor Account_To = accounts.getAccount(ToAccount_ID);
		Cursor Account_From = accounts.getAccount(FromAccount_ID);
		
		Account_To.moveToNext();
		Account_From.moveToNext();*/
		
		double moneyInFromAccount = acctFrom.getDouble(acctFrom.getColumnIndex(ACCOUNT_TOTAL));
		double moneyInToAccount = acctTo.getDouble(acctTo.getColumnIndex(ACCOUNT_TOTAL));
		
		if((Double.parseDouble(newInfo[AMOUNT]) <= Double.parseDouble(oldInfo[AMOUNT])) 
				|| (moneyInFromAccount - (Double.parseDouble(newInfo[AMOUNT]) - Double.parseDouble(oldInfo[AMOUNT])) >= 0))
		{
			transactions.updateTransfer(trans_ID, newInfo);
			
			EditAccountTotal(Long.parseLong(oldInfo[FROM]), moneyInFromAccount + Double.parseDouble(oldInfo[AMOUNT]));
			EditAccountTotal(Long.parseLong(oldInfo[TO]),  moneyInToAccount - Double.parseDouble(oldInfo[AMOUNT]));
			
			EditAccountTotal(Long.parseLong(newInfo[FROM]), moneyInFromAccount - Double.parseDouble(newInfo[AMOUNT]));
			EditAccountTotal(Long.parseLong(newInfo[TO]), moneyInToAccount + Double.parseDouble(newInfo[AMOUNT]));
			
			return true;
		}
		return false;
	}
	/** Method is used to create an expense transaction.  An expense transaction is one that removes money to an account and places that money into an categorys value.
	*	@param FromAccount_ID Account the money is being deducted from
	*	@param Amount The value of the currency being moved from the from account
	*	@param Note A string that contains information about the reason for the transaction
	*	@param Date Date the transaction is being created
	*	@param Category_ID The category or reason that the expense is occurring
	*/
	public boolean Expense(String[] newInfo) {
		Cursor Account_From = accounts.getAccount(Long.parseLong(newInfo[FROM]));
		Cursor Category = categories.getCategory(Long.parseLong(newInfo[CATEGORY]));
		Account_From.moveToNext();
		Category.moveToNext();
		double moneyInAccount = Account_From.getDouble(Account_From.getColumnIndex(ACCOUNT_TOTAL));
		//Check to see if there is enough moolah in the account
		if(moneyInAccount >= Double.parseDouble(newInfo[AMOUNT])){
			transactions.createExpense(newInfo);
			EditAccountTotal(Long.parseLong(newInfo[FROM]), moneyInAccount - Double.parseDouble(newInfo[AMOUNT]));
			EditCategoryTotal(Long.parseLong(newInfo[CATEGORY]), Category.getDouble(Category.getColumnIndex(CATEGORY_TOTAL)) + Double.parseDouble(newInfo[AMOUNT]));
			return true;
		}
		return false;
	}
	
	public boolean updateExpense(long trans_ID, String[] oldInfo, String newInfo[])
	{
		
		Cursor trans = transactions.getTransaction(trans_ID);
		Cursor acct = accounts.getAccount(Long.parseLong(oldInfo[FROM]));
		Cursor cat = categories.getCategory(Long.parseLong(oldInfo[CATEGORY]));
		
		trans.moveToFirst();
		acct.moveToFirst();
		cat.moveToFirst();
		
		/*double oldAmount = trans.getDouble(trans.getColumnIndex(TRANSACTION_AMOUNT));
		long oldFromAccountId = acct.getLong(acct.getColumnIndex(_ID));
		long oldCategoryId = cat.getLong(cat.getColumnIndex(_ID));*/
		
		/*Cursor Account_From = accounts.getAccount(FromAccount_ID);
		Cursor Category = categories.getCategory(Category_ID);

		Account_From.moveToNext();
		Category.moveToNext();*/
		
		double moneyInAccount = acct.getDouble(acct.getColumnIndex(ACCOUNT_TOTAL));
		double moneyInCategory = cat.getDouble(cat.getColumnIndex(CATEGORY_TOTAL));
		
		if((Double.parseDouble(newInfo[AMOUNT]) <= Double.parseDouble(oldInfo[AMOUNT])) 
				|| (moneyInAccount - (Double.parseDouble(newInfo[AMOUNT]) - Double.parseDouble(oldInfo[AMOUNT])) >= 0))
		{
			transactions.updateExpense(trans_ID, newInfo);
			
			EditAccountTotal(Long.parseLong(oldInfo[FROM]), moneyInAccount + Double.parseDouble(oldInfo[AMOUNT]));
			EditCategoryTotal(Long.parseLong(oldInfo[CATEGORY]), moneyInCategory - Double.parseDouble(oldInfo[AMOUNT]));
			
			EditAccountTotal(Long.parseLong(newInfo[FROM]), moneyInAccount - Double.parseDouble(newInfo[AMOUNT]));
			EditCategoryTotal(Long.parseLong(newInfo[CATEGORY]), moneyInCategory + Double.parseDouble(newInfo[AMOUNT]));
			return true;
		}
		return false;
	}
	/** Method is used to create an Income transaction.  An income transaction is one that added money to an account and to a category from thin air.
	*	@param ToAccount_ID Account the money is being added to
	*	@param Amount The value of the currency being moved into the ToAccount
	*	@param Note A string that contains information about the reason for the transaction
	*	@param Date Date the transaction is being created
	*	@param Category_ID The category or reason that the expense is occurring	
	*/
	public void Income(String[] newInfo) {
		
		transactions.createIncome(newInfo);
		Cursor Account_To = accounts.getAccount(Long.parseLong(newInfo[TO]));
		Cursor Category = categories.getCategory(Long.parseLong(newInfo[CATEGORY]));

		Account_To.moveToNext();
		Category.moveToNext();
		EditAccountTotal(Long.parseLong(newInfo[TO]), Account_To.getDouble(Account_To.getColumnIndex(ACCOUNT_TOTAL)) + Double.parseDouble(newInfo[AMOUNT]));
		EditCategoryTotal(Long.parseLong(newInfo[CATEGORY]), Category.getDouble(Category.getColumnIndex(CATEGORY_TOTAL)) + Double.parseDouble(newInfo[AMOUNT]));
		
	}
	
	public void updateIncome(long trans_ID, String[] oldInfo, String[] newInfo) {
		
		Cursor trans = transactions.getTransaction(trans_ID);
		Cursor newToAccount = accounts.getAccount(Long.parseLong(newInfo[TO]));
		Cursor newCategory = categories.getCategory(Long.parseLong(newInfo[CATEGORY]));
		Cursor oldAccount = accounts.getAccount(Long.parseLong(oldInfo[TO]));
		Cursor oldCategory = categories.getCategory(Long.parseLong(oldInfo[CATEGORY]));
		
		trans.moveToFirst();
		newToAccount.moveToFirst();
		newCategory.moveToFirst();
		oldAccount.moveToFirst();
		oldCategory.moveToFirst();
		
		transactions.updateIncome(trans_ID, newInfo);

		/*********** THIS IS NOT OCCURING *****************/
		EditAccountTotal(Long.parseLong(oldInfo[TO]), oldAccount.getDouble(oldAccount.getColumnIndex(ACCOUNT_TOTAL)) - Double.parseDouble(oldInfo[AMOUNT]));
		EditCategoryTotal(Long.parseLong(oldInfo[CATEGORY]), oldCategory.getDouble(oldCategory.getColumnIndex(CATEGORY_TOTAL)) - Double.parseDouble(oldInfo[AMOUNT]));		
		/**************************************************/
		EditAccountTotal(Long.parseLong(newInfo[TO]), newToAccount.getDouble(newToAccount.getColumnIndex(ACCOUNT_TOTAL)) + Double.parseDouble(newInfo[AMOUNT]));
		EditCategoryTotal(Long.parseLong(newInfo[CATEGORY]), newCategory.getDouble(newCategory.getColumnIndex(CATEGORY_TOTAL)) + Double.parseDouble(newInfo[AMOUNT]));	
	
		
	}
	/** Method is a delegated version of getTransactions(). This method should always be used instead of the transactions Version directly.
	*	@return a cursor from the database containing all transactions
	*/
	public Cursor getTransactions() {
		return transactions.getTransactions();
	}
	public Cursor getTransactions(String FromDate, String ToDate) {
		return transactions.getTransactions(FromDate,ToDate);
	}
	public Cursor getTransaction(long transID){
		return transactions.getTransaction(transID);
	}
/*	
 	void deleteTransaction(double transID){
		transactions.deleteTransaction(transID);
	}
*/
	/** Undo and deleted transaction from log
	 * @param trans_ID transaction to be deleted
	 */
	public void deleteTransaction(long trans_ID)
	{
		Cursor trans = transactions.getTransaction(trans_ID);
		trans.moveToFirst();
		
		double Amount = trans.getDouble(trans.getColumnIndex(TRANSACTION_AMOUNT));
		
		long ToAccount_ID = trans.getInt(trans.getColumnIndex(TRANSACTION_TOACCOUNT));
		long FromAccount_ID = trans.getInt(trans.getColumnIndex(TRANSACTION_FROMACCOUNT));
		long Category_ID = trans.getInt(trans.getColumnIndex(TRANSACTION_CATEGORY));
		
		Cursor Account_To = accounts.getAccount(ToAccount_ID);
		Cursor Account_From = accounts.getAccount(FromAccount_ID);
		Cursor Category = categories.getCategory(Category_ID);
		
		Account_To.moveToFirst();
		Account_From.moveToFirst();
		Category.moveToFirst();
		
		if(Account_To.getCount()!= 0)
			EditAccountTotal(ToAccount_ID, Account_To.getDouble(Account_To.getColumnIndex(ACCOUNT_TOTAL)) - Amount);
		if(Category.getCount()!= 0)
			EditCategoryTotal(Category_ID, Category.getDouble(Category.getColumnIndex(CATEGORY_TOTAL)) - Amount);
		if(Account_From.getCount()!= 0)
			EditAccountTotal(FromAccount_ID, Account_From.getDouble(Account_From.getColumnIndex(ACCOUNT_TOTAL)) + Amount);
		
		transactions.removeTransaction(trans_ID);
	}
	/** Method is a delegated version of ClearTransTable(). This method should always be used instead of the transactions Version directly.
	*/
	void ClearTransTable()
	{
		transactions.ClearTable();
	}
}
