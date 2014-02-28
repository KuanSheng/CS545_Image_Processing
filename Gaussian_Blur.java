import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;



public class Gaussian_Blur implements PlugInFilter {


    public int setup(String arg, ImagePlus imp) {
        return DOES_32;
    }

    
    float[] makeGaussKernel1d(double sigma) {
    	
    	 // create the kernel
    	 int center = (int) (3.0*sigma);
    	 float[] kernel = new float[2*center+1]; // odd size
    	
    	 // fill the kernel
    	 double sigma2 = sigma * sigma; // σ2
    	 for (int i=0; i<kernel.length; i++) {
	    	 double r = center - i;
	    	 kernel[i] = (float) Math.exp(-0.5 * (r*r) / sigma2);
    	 }
    	
    	 return kernel;
    }
    


    public void run(ImageProcessor orig) {
        int w = orig.getWidth();
        int h = orig.getHeight();
        //3x3 filter matrix
        float[] filter = makeGaussKernel1d(10);
        
        ImageProcessor copy = orig.duplicate();

		for (int v = 1; v <= h - 2; v++) {
			for (int u = 1; u <= w - 2; u++) {
				// compute filter result for position (u,v)
				double sum = 0;
				for (int j = -1; j <= 1; j++) {
					for (int i = -1; i <= 1; i++) {
						int p = copy.getPixel(u + i, v + j);
						// get the corresponding filter coefficient
						double c = filter[10];// error
						sum = sum + c * p;
					}
				}
				int q = (int) sum;
				orig.putPixel(u, v, q);
			}
		}
    }

}
