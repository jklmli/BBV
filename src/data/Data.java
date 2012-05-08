package data;

public class Data {
	private byte[] data;

    public Data(byte[] data) {
            this.data = data;
    }

    public byte[] getBytes() {
            return data;
    }

    public boolean equals(Object otherObject)
    {
            if(!(otherObject instanceof Data))
            {
                    return false;
            }

            Data otherData = (Data) otherObject;
            return data.equals(otherData.data);
    }

    public int hashCode()
    {
            return data.hashCode();
    }

}
