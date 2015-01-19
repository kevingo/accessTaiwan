package accesstaiwan.model.transportation.hsr;

import java.util.ArrayList;
import java.util.Arrays;

public class Train {
	private String trainId;
	private String[] onlyOnDayOfWeek;
	/*
	 * Embedded
	 */
	private ArrayList<StopTime> stopTimes;
	/*
	 * Embedded
	 */

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	public String[] getOnlyOnDayOfWeek() {
		return onlyOnDayOfWeek;
	}

	public void setOnlyOnDayOfWeek(String[] onlyOnDayOfWeek) {
		this.onlyOnDayOfWeek = onlyOnDayOfWeek;
	}

	public ArrayList<StopTime> getStopTimes() {
		return stopTimes;
	}

	public void setStopTimes(ArrayList<StopTime> stopTimes) {
		this.stopTimes = stopTimes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(onlyOnDayOfWeek);
		result = prime * result
				+ ((stopTimes == null) ? 0 : stopTimes.hashCode());
		result = prime * result + ((trainId == null) ? 0 : trainId.hashCode());
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
		Train other = (Train) obj;
		if (!Arrays.equals(onlyOnDayOfWeek, other.onlyOnDayOfWeek))
			return false;		
		if (stopTimes == null) {
			if (other.stopTimes != null)
				return false;
		} else if (!stopTimes.equals(other.stopTimes))
			return false;
		if (trainId == null) {
			if (other.trainId != null)
				return false;
		} else if (!trainId.equals(other.trainId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Train [trainId=" + trainId + ", onlyOnDayOfWeek="
				+ Arrays.toString(onlyOnDayOfWeek) + ", stopTimes=" + stopTimes + "]";
	}

}
