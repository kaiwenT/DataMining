package com.hust.datamining.algorithm.neutral;

import java.util.List;

public class BPNeutralNet {
    /**
     * Logger for this class
     */
//    private static final Logger logger = LoggerFactory.getLogger(BPNeutralNet.class);

    private static final int LAYER = 3;
    private static final int NUM = 30;
    private static final double A = 30;
    private static final double B = 10;
    private static final int ITERS = 1000;
    private static final double ETA_W = 0.0035;
    private static final double ETA_B = 0.001;
    private static final double ERROR = 0.002;
    private static final double ACCU = 0.005;

    private List<double[]> inputs;
    private List<double[]> outputs;

    private int in_num;
    private int ou_num;
    private int hd_num;

    private double w[][][];
    private double b[][];
    private double x[][];
    private double d[][];

    private void setNUMS() {
        in_num = inputs.get(0).length;
        ou_num = outputs.get(0).length;
        hd_num = (int) (Math.sqrt((in_num + ou_num) * 1.0) + 5);
        if (hd_num > NUM) {
            hd_num = NUM;
        }
    }

    private void initNetWork() {
        w = new double[LAYER][NUM][NUM];
        b = new double[LAYER][NUM];
        x = new double[LAYER][NUM];
        d = new double[LAYER][NUM];
    }

    public void train() {
        // logger.debug("Begin to train BP Network!!!");
        System.out.println("Begin to train BP Network!!!");
        setNUMS();
        initNetWork();
        int num = inputs.size();
        for (int iter = 0; iter < ITERS; iter++) {
            for (int cnt = 0; cnt < num; cnt++) {
                for (int i = 0; i < in_num; i++) {
                    x[0][i] = inputs.get(cnt)[i];
                }
                while (true) {
                    forwardTransfer();
                    if (getError(cnt) < ERROR) {
                        break;
                    }
                    reverseTransfer(cnt);
                }
            }
            // logger.debug("This is the {}th tranining NetWork!", iter);
            System.out.println("This is the " + iter + "th tranining NetWork!");
            double accu = getAccu();
            // logger.debug("All Samples Accuracy is{}", accu);
            System.out.println("All Samples Accuracy is " + accu);
            if (accu < ACCU) {
                break;
            }
        }
        // logger.debug("The BP Network Train End!");
        System.out.println("The BP Network Train End!");
    }

    public double[] foreCast(double[] data) {
        if (data.length != in_num) {
            throw new IllegalArgumentException("The length of input data is not equal to the num of input neutral");
        }
        for (int i = 0; i < in_num; i++) {
            x[0][i] = data[i];
        }
        forwardTransfer();
        double[] out = new double[ou_num];
        for (int i = 0; i < ou_num; i++) {
            out[i] = x[2][i];
        }
        return out;
    }

    private void forwardTransfer() {
        for (int j = 0; j < hd_num; j++) {
            double t = 0;
            for (int i = 0; i < in_num; i++) {
                t += w[1][i][j] * x[0][i];
            }
            t += b[1][j];
            x[1][j] = sigmoid(t);
        }
        for (int j = 0; j < ou_num; j++) {
            double t = 0;
            for (int i = 0; i < hd_num; i++) {
                t += w[2][i][j] * x[1][j];
            }
            t += b[2][j];
            x[2][j] = sigmoid(t);
        }
    }

    private double getError(int cnt) {
        double ans = 0;
        for (int i = 0; i < ou_num; i++) {
            ans += 0.5 * (x[2][i] - outputs.get(cnt)[i]) * (x[2][i] - outputs.get(cnt)[i]);
        }
        return ans;
    }

    private void reverseTransfer(int cnt) {
        calcDelta(cnt);
        updateNetwork();
    }

    private double getAccu() {
        double ans = 0;
        int num = inputs.size();
        for (int i = 0; i < num; i++) {
            int m = inputs.get(i).length;
            for (int j = 0; j < m; j++) {
                x[0][j] = inputs.get(i)[j];
            }
            forwardTransfer();
            int n = outputs.get(i).length;
            for (int j = 0; j < n; j++) {
                ans = 0.5 * (x[2][j] - outputs.get(i)[j]) * (x[2][j] - outputs.get(i)[j]);
            }
        }
        return ans / num;
    }

    private void calcDelta(int cnt) {
        for (int i = 0; i < ou_num; i++) {
            d[2][i] = (x[2][i] - outputs.get(cnt)[i]) * x[2][i] * (A - x[2][i]) / (A * B);
        }
        for (int i = 0; i < hd_num; i++) {
            double t = 0;
            for (int j = 0; j < ou_num; j++) {
                t += w[2][i][j] * d[2][j];
            }
            d[1][i] = t * x[1][i] * (A - x[1][i]) / (A * B);
        }
    }

    private void updateNetwork() {
        // 隐含层和输出层之间权值和阀值调整
        for (int i = 0; i < hd_num; i++) {
            for (int j = 0; j < ou_num; j++)
                w[2][i][j] -= ETA_W * d[2][j] * x[1][i];
        }
        for (int i = 0; i < ou_num; i++)
            b[2][i] -= ETA_B * d[2][i];

        // 输入层和隐含层之间权值和阀值调整
        for (int i = 0; i < in_num; i++) {
            for (int j = 0; j < hd_num; j++)
                w[1][i][j] -= ETA_W * d[1][j] * x[0][i];
        }
        for (int i = 0; i < hd_num; i++)
            b[1][i] -= ETA_B * d[1][i];
    }

    private double sigmoid(double x) {
        return A / (1 + Math.pow(Math.E, -x / B));
    }

    public List<double[]> getInputs() {
        return inputs;
    }

    public void setInputs(List<double[]> inputs) {
        this.inputs = inputs;
    }

    public List<double[]> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<double[]> outputs) {
        this.outputs = outputs;
    }
}
