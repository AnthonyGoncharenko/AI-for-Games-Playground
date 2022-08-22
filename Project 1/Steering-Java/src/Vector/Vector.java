package Vector;

import java.util.ArrayList;

public class Vector {
    protected ArrayList<Double> vec;

    public Vector(double... nums) {
        vec = new ArrayList<>();
        for (double num : nums) {
            vec.add(num);
        }
    }
    public void print(){
        System.out.println(this.vecToString());
    }
    private String vecToString(){
        StringBuilder res = new StringBuilder("Vector(");
        for (double num : this.vec){
            res.append(String.format("%4.4f, ", num));
        }
        res.deleteCharAt(res.lastIndexOf(" "));
        res.deleteCharAt(res.lastIndexOf(","));
        res.append(")");
        return res.toString();
    }
    private void add(double v) {
        this.vec.add(v);
    }

    public double get(int i) {
        return this.vec.get(i);
    }

    private int size() {
        return this.vec.size();
    }

    public double magnitude() {
        return Math.sqrt(this.dotProduct(this));
    }

    public Vector addConstant(double k) {
        Vector res = new Vector();
        for (Double aDouble : this.vec) {
            res.add(aDouble + k);
        }
        return res;
    }

    public Vector multiplyConstant(double k) {
        Vector res = new Vector();
        for (Double aDouble : this.vec) {
            res.add(aDouble * k);
        }
        return res;
    }

    public Vector normalize() {
        return this.multiplyConstant(1 / magnitude());
    }

    public double dotProduct(Vector vector2) {
        double res = 0;
        if (this.vec.size() != vector2.size()) throw new RuntimeException("Arrays must be same size");
        for (int i = 0; i < this.vec.size(); i++) {
            res += this.vec.get(i) * vector2.get(i);
        }
        return res;
    }

    public Vector elementWiseMultiplication(Vector vector2) {
        if (this.vec.size() != vector2.size()) throw new RuntimeException("Arrays must be same size");
        Vector res = new Vector();
        for (int i = 0; i < this.vec.size(); i++) {
            res.add(this.vec.get(i) * vector2.get(i));
        }
        return res;
    }

    public Vector elementWiseSubtraction(Vector vector2) {
        if (this.vec.size() != vector2.size()) throw new RuntimeException("Arrays must be same size");
        Vector res = new Vector();
        for (int i = 0; i < this.vec.size(); i++) {
            res.add(this.vec.get(i) - vector2.get(i));
        }
        return res;
    }

    public Vector elementWiseAddition(Vector vector2) {
        if (this.vec.size() != vector2.size()) throw new RuntimeException("Arrays must be same size");
        Vector res = new Vector();
        for (int i = 0; i < this.vec.size(); i++) {
            res.add(this.vec.get(i) + vector2.get(i));
        }
        return res;
    }

    public Vector elementWiseDivision(Vector vector2) {
        if (this.vec.size() != vector2.size()) throw new RuntimeException("Arrays must be same size");
        Vector res = new Vector();
        for (int i = 0; i < this.vec.size(); i++) {
            res.add(this.vec.get(i) / vector2.get(i));
        }
        return res;
    }
}
