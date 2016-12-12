package lightcycles.math;

import java.nio.FloatBuffer;

import lightcycles.util.BufferUtil;

public class Matrix4f {
	private static final int SIZE = 16;
	private float[] matrices;
	
	public Matrix4f() {
		matrices = new float[SIZE];
		matrices[0]  = 1.0f;
		matrices[5]  = 1.0f;
		matrices[10] = 1.0f;
		matrices[15] = 1.0f;
	}
	
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f result = new Matrix4f();
		
		result.matrices[0 + 0 * 4] = 2.0f / (right - left);

		result.matrices[1 + 1 * 4] = 2.0f / (top - bottom);

		result.matrices[2 + 2 * 4] = 2.0f / (near - far);
		
		result.matrices[0 + 3 * 4] = (left + right) / (left - right);
		result.matrices[1 + 3 * 4] = (bottom + top) / (bottom - top);
		result.matrices[2 + 3 * 4] = (far + near) / (far - near);
		
		return result;
	}
	
	public static Matrix4f scale(Vector3f vector) {
		Matrix4f result = new Matrix4f();
		
		result.matrices[0]  = vector.x;
		result.matrices[5]  = vector.y;
		result.matrices[10] = vector.z;
		
		return result;
	}
	
	public static Matrix4f translate(Vector3f vector) {
		Matrix4f result = new Matrix4f();
		
		result.matrices[0 + 3 * 4] = vector.x;
		result.matrices[1 + 3 * 4] = vector.y;
		result.matrices[2 + 3 * 4] = vector.z;
		
		return result;
	}
	
	public static Matrix4f rotate(float angle) {
		Matrix4f result = new Matrix4f();
		
		float r = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(r);
		float sin = (float) Math.sin(r);
		
		result.matrices[0 + 0 * 4] = cos;
		result.matrices[1 + 0 * 4] = sin;
		
		result.matrices[0 + 1 * 4] = -sin;
		result.matrices[1 + 1 * 4] = cos;
		
		return result;
	}
	
	public Matrix4f multiply(Matrix4f matrix) {
		Matrix4f result = new Matrix4f();
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += this.matrices[x + e * 4] * matrix.matrices[e + y * 4]; 
				}			
				result.matrices[x + y * 4] = sum;
			}
		}
		
		return result;
	}
	
	public FloatBuffer toFloatBuffer() {
		return BufferUtil.createFloatBuffer(matrices);
	}
}
