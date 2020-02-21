import java.util.TreeMap;

import org.apache.commons.io.IOUtils;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class BookScanning{
	
	List<Integer> bookScoreList;
	TreeMap<Integer, Library> libraries;
	TreeMap<Integer, String> bl;
	List<String> booksScanned;

	int B, L, D;  /* here B = total number of books, L = total number of libraries, D = available days */
	int tBooks, tDays, tBooksPerDay;

	void getData(String filepath) throws FileNotFoundException{
		libraries = new TreeMap<Integer, Library>(Collections.reverseOrder());		
		File file = new File(filepath);
		//BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			FileInputStream fisTargetFile = new FileInputStream(new File(filepath));

			String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
			//System.out.println("filedata : " + targetFileStr);
			String[] fileData = targetFileStr.split("\n");
			int line = 0;
			
			String[] params = fileData[line].split(" ");
			
			//System.out.println("params :: " + params.toString());
			B = Integer.parseInt(params[0]);
			L = Integer.parseInt(params[1]);
			D = Integer.parseInt(params[2]);

			line++;
			params = fileData[line].split(" ");
			bookScoreList = new ArrayList<Integer>();
			for(String s : params){
				bookScoreList.add(Integer.parseInt(s));
			}

			int i = 0;
			int libid = 0;
			while(i < L){
				line++;
				params =fileData[line].split(" ");
				tBooks = Integer.parseInt(params[0]);
				tDays = Integer.parseInt(params[1]);
				tBooksPerDay = Integer.parseInt(params[2]);

				line++;
				params =fileData[line].split(" ");
				bl = new TreeMap<Integer,String>(Collections.reverseOrder());
				
				for(String s : params){
					int x = Integer.parseInt(s);
					bl.put(bookScoreList.get(x), s);
				}

				Library library = new Library(libid, tBooks, tDays, tBooksPerDay, bl);
				libraries.put(tDays, library);

				libid++;
				i++;
			}			
		} catch (Exception e) {
			System.out.println("EXCEPTION : " + e.getMessage());
			e.printStackTrace();
		}
		try {
			
		} 
		finally {
			//sc.close();
		}
	}

	void GenerateOutput(){		
		//System.out.println("libraries = " + libraries.toString());		
		TreeMap<Integer, Library> result = new TreeMap<Integer, Library>(Collections.reverseOrder());
		booksScanned =  new ArrayList<String>();		
		int day = 0;
		
		for(Map.Entry<Integer, Library> entry : libraries.entrySet() ) {
			if(day > D)
				break;			
			Library lib = (Library) entry.getValue();		
			day += lib.getDaysRequiredForSignup();

			//int days_required = lib.getMaxBooksPerDay() * (D - day);
			
			result.put(entry.getKey(), lib);			
		}
		
		System.out.println(result.size());
		day = 0;
		for(Map.Entry<Integer, Library> entry : result.entrySet() ) {
			//System.out.println("day = " + day);
			if(day >= D)
				break;
			List<String> booksToBeScanned = new ArrayList<String>();
			
			Library lib = (Library) entry.getValue();		
			day += lib.getDaysRequiredForSignup();
			int days_required = lib.getMaxBooksPerDay() * (D - day);
			TreeMap<Integer, String> books = lib.getBookList();
			
			int s =  books.size();			
			int i=0;
			for(Map.Entry<Integer, String> en : books.entrySet()) {
				//if(booksScanned.contains(en.getValue()))
					//continue;
				booksToBeScanned.add(en.getValue());
				booksScanned.add(en.getValue());				
				i++;
			}			
			System.out.println(lib.getLibrryId() + " " + booksToBeScanned.size());
			
			for(i=0, s = booksToBeScanned.size(); i < s; i++) {
				if(i == s - 1)
					System.out.print(booksToBeScanned.get(i) + "\n");
				else
					System.out.print(booksToBeScanned.get(i) + " ");
			}
			//System.out.println("day = " + day);
		}		
	}

	// driver program
	public static void main(String[] args){
		String filepath = "E://P~L~A~C~E~M~E~N~T//hashcode//testecases";
		String filename = "b_read_on.txt";
		
		BookScanning bs = new BookScanning();
		
		try {
			bs.getData( filepath + File.separator + filename);
			bs.GenerateOutput();	
		} catch (Exception e) {
			System.out.println("EXCEPTION IN MAIN");
			e.printStackTrace();
		}
	}
}

class Library{
	int librryId, totalBooks, daysRequiredForSignup, maxBooksPerDay;
	TreeMap<Integer, String> bookList;		
	
	public Library() {
		super();
	}

	@Override
	public String toString() {
		return "Library [librryId=" + librryId + ", totalBooks=" + totalBooks + ", daysRequiredForSignup="
				+ daysRequiredForSignup + ", maxBooksPerDay=" + maxBooksPerDay + ", bookList=" + bookList + "]";
	}

	public Library(int librryId, int totalBooks, int daysRequiredForSignup, int maxBooksPerDay,
			TreeMap<Integer, String> bookList) {
		super();
		this.librryId = librryId;
		this.totalBooks = totalBooks;
		this.daysRequiredForSignup = daysRequiredForSignup;
		this.maxBooksPerDay = maxBooksPerDay;
		this.bookList = bookList;
	}
	
	public int getLibrryId() {
		return librryId;
	}
	public void setLibrryId(int librryId) {
		this.librryId = librryId;
	}
	public int getTotalBooks() {
		return totalBooks;
	}
	public void setTotalBooks(int totalBooks) {
		this.totalBooks = totalBooks;
	}
	public int getDaysRequiredForSignup() {
		return daysRequiredForSignup;
	}
	public void setDaysRequiredForSignup(int daysRequiredForSignup) {
		this.daysRequiredForSignup = daysRequiredForSignup;
	}
	public int getMaxBooksPerDay() {
		return maxBooksPerDay;
	}
	public void setMaxBooksPerDay(int maxBooksPerDay) {
		this.maxBooksPerDay = maxBooksPerDay;
	}
	public TreeMap<Integer, String> getBookList() {
		return bookList;
	}
	public void setBookList(TreeMap<Integer, String> bookList) {
		this.bookList = bookList;
	}	
}
