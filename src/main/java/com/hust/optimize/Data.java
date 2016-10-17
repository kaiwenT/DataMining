package com.hust.optimize;

public class Data {
    private float value;
    private int index;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        Data data = (Data) obj;
        if (this.value != data.value || this.index != data.index) {
            return false;
        }
        return true;
    }

}
