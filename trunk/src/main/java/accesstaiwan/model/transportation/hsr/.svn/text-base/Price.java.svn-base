package accesstaiwan.model.transportation.hsr;

import java.util.Arrays;

public class Price {

	private String[] stopPair;
	private int business;
	private int businessConcessionary;
	private int businessGroup;
	private int standard;
	private int standardConcessionary;
	private int standardGroup;
	private int nonreserved;
	private int nonreservedConcessionary;

	public String[] getStopPair() {
		return stopPair;
	}

	public void setStopPair(String[] stopPair) {
		this.stopPair = stopPair;
	}

	public int getBusiness() {
		return business;
	}

	public void setBusiness(int business) {
		this.business = business;
	}

	public int getBusinessConcessionary() {
		return businessConcessionary;
	}

	public void setBusinessConcessionary(int businessConcessionary) {
		this.businessConcessionary = businessConcessionary;
	}

	public int getBusinessGroup() {
		return businessGroup;
	}

	public void setBusinessGroup(int businessGroup) {
		this.businessGroup = businessGroup;
	}

	public int getStandard() {
		return standard;
	}

	public void setStandard(int standard) {
		this.standard = standard;
	}

	public int getStandardConcessionary() {
		return standardConcessionary;
	}

	public void setStandardConcessionary(int standardConcessionary) {
		this.standardConcessionary = standardConcessionary;
	}

	public int getStandardGroup() {
		return standardGroup;
	}

	public void setStandardGroup(int standardGroup) {
		this.standardGroup = standardGroup;
	}

	public int getNonreserved() {
		return nonreserved;
	}

	public void setNonreserved(int nonreserved) {
		this.nonreserved = nonreserved;
	}

	public int getNonreservedConcessionary() {
		return nonreservedConcessionary;
	}

	public void setNonreservedConcessionary(int nonreservedConcessionary) {
		this.nonreservedConcessionary = nonreservedConcessionary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + business;
		result = prime * result + businessConcessionary;
		result = prime * result + businessGroup;
		result = prime * result + nonreserved;
		result = prime * result + nonreservedConcessionary;
		result = prime * result + standard;
		result = prime * result + standardConcessionary;
		result = prime * result + standardGroup;
		result = prime * result + Arrays.hashCode(stopPair);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Price other = (Price) obj;
		if (business != other.business)
			return false;
		if (businessConcessionary != other.businessConcessionary)
			return false;
		if (businessGroup != other.businessGroup)
			return false;
		if (nonreserved != other.nonreserved)
			return false;
		if (nonreservedConcessionary != other.nonreservedConcessionary)
			return false;
		if (standard != other.standard)
			return false;
		if (standardConcessionary != other.standardConcessionary)
			return false;
		if (standardGroup != other.standardGroup)
			return false;
		if (!Arrays.equals(stopPair, other.stopPair))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Price [stopPair=" + Arrays.toString(stopPair) + ", business="
				+ business + ", businessConcessionary=" + businessConcessionary
				+ ", businessGroup=" + businessGroup + ", standard=" + standard
				+ ", standardConcessionary=" + standardConcessionary
				+ ", standardGroup=" + standardGroup + ", nonreserved="
				+ nonreserved + ", nonreservedConcessionary="
				+ nonreservedConcessionary + "]";
	}

}
