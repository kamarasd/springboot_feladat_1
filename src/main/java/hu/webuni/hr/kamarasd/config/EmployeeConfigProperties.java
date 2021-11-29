package hu.webuni.hr.kamarasd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "employee")
@Component
public class EmployeeConfigProperties {
	
	private Smart smart = new Smart();

	public Smart getSmart() {
		return smart;
	}

	public void setSmart(Smart smart) {
		this.smart = smart;
	}

	public static class Smart{
		
		public Raise getRaise() {
			return raise;
		}
		public void setRaise(Raise raise) {
			this.raise = raise;
		}
		public Percent getPercent() {
			return percent;
		}
		public void setPercent(Percent percent) {
			this.percent = percent;
		}
		public Default getDef() {
			return def;
		}
		public void setDef(Default def) {
			this.def = def;
		}
		private Raise raise = new Raise();
		private Percent percent = new Percent();
		private Default def = new Default();
		
	}
	
	public static class Default{
		private int defPercent;

		public int getDefPercent() {
			return defPercent;
		}

		public void setDefPercent(int defPercent) {
			this.defPercent = defPercent;
		}
	}
	
	public static class Raise{
		
		public int getYearTwoHalf() {
			return yearTwoHalf;
		}
		public void setYearTwoHalf(int yearTwoHalf) {
			this.yearTwoHalf = yearTwoHalf;
		}
		public int getYearFive() {
			return yearFive;
		}
		public void setYearFive(int yearFive) {
			this.yearFive = yearFive;
		}
		public int getYearTen() {
			return yearTen;
		}
		public void setYearTen(int yearTen) {
			this.yearTen = yearTen;
		}
		
		private int yearTwoHalf;
		private int yearFive;
		private int yearTen;
	}
	
	public static class Percent{
		
		public int getLessThenTwo() {
			return lessThenTwo;
		}
		public void setLessThenTwo(int lessThenTwo) {
			this.lessThenTwo = lessThenTwo;
		}
		public int getMoreThanTwo() {
			return moreThanTwo;
		}
		public void setMoreThanTwo(int moreThanTwo) {
			this.moreThanTwo = moreThanTwo;
		}
		public int getFive() {
			return five;
		}
		public void setFive(int five) {
			this.five = five;
		}
		public int getTen() {
			return ten;
		}
		public void setTen(int ten) {
			this.ten = ten;
		}
		
		private int lessThenTwo;
		private int moreThanTwo;
		private int five;
		private int ten;
	}
}
