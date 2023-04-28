public class Transfer {
    private final int transpType;   // one of "bus", "train", "cablecar", "ferry"

    private final double minTransferTime;

    private final Stop fromStop;
    private final Stop toStop;

    public Transfer(int transpType, int minTransferTime, Stop fromStop, Stop toStop) {
        this.transpType = transpType;
        this.minTransferTime = minTransferTime;
        this.fromStop = fromStop;
        this.toStop = toStop;
    }

    public int getTranspType() {
       // get transport type from int
        return transpType;
    }

    public double getMinTransferTime() {
        return minTransferTime;
    }

    public Stop getFromStop() {
        return fromStop;
    }

    public Stop getToStop() {
        return toStop;
    }

    public String toString() {
        if(fromStop == null || toStop == null) {
            return "Transfer: From: null, To: null - Transport type: " + transpType;
        }
        return "Transfer: From:" + fromStop.getName() + ", To:" + toStop.getName() + " - Transport type: " + transpType;
    }
}
