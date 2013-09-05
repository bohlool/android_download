package com.appdear.client.commctrls;





import com.appdear.client.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class MpointView extends TextView {

	private int totalPic = -1; //ͼƬ������
	private int currentPicPos = -1;  // ��ǰ��ʾ��ͼƬ����������ʾ��ʱ�ڼ��ţ�
	
	private int width;  
	private int height;  
	
	private int xStartPos; //��������ߵ�һ��ԲȦ��x��λ�ã������������λ��ƣ�
	private int yPos; //���Ƶ�Բ��y��λ��
	private int yPosOffset; //Y���ƫ����
	
	private int pointDistance; //����Բ���㣩֮��ľ���
	private int pointRadius; //Բ���㣩�İ뾶 ֱ��
	private int pointDiameter; //Բ���㣩��ֱ��
	
	public MpointView(Context context) {
		super(context);
	}

	public MpointView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Bitmap greenPoint = BitmapFactory.decodeResource(getResources(), R.drawable.adpoints2);
		Bitmap whitePoint = BitmapFactory.decodeResource(getResources(), R.drawable.adpoints);
		
		//���������ΰڷ�ͼƬ
		for (int i = 0; i < this.totalPic; i++) {
			if (this.currentPicPos - 1  == i) {
				//����ǵ�ǰͼƬ��Ӧ��Բ������������ʾ
				canvas.drawBitmap(greenPoint, 
						xStartPos + i * (pointDistance + pointRadius), 
						this.yPos, null);
				
				continue;
			}
			//����Բ����ʾ��ͨ��ʾ
			canvas.drawBitmap(whitePoint, 
					xStartPos + i * (pointDistance + pointRadius), 
					this.yPos, null);
		}
	}
	
	/**
	 * ˢ������
	 * @param totalPic  ��ͼƬ��
	 * @param currentPicPos ��ǰͼƬ����
	 */
	public void refreshData(int totalPic, int currentPicPos) {
		this.totalPic = totalPic;
		this.currentPicPos = currentPicPos;
		
		//�����Բ��y��λ����x�����ʼ����λ��
		/**
		 * �㷨��
		 * ���ͼƬ��Ϊż������x�����ʼλ��Ϊ�ܿ�ȣ�width����һ���ȥ��n-1������ֱ��+���룩���ټ�ȥһ���뾶��������
		 * ���ͼƬ��Ϊ��������x�����ʼλ��Ϊ�ܿ�ȣ�width����һ���ȥn����ֱ��+���룩
		 */
		if (totalPic % 2 == 0) {  //ż��
			this.xStartPos = this.width - 
					(totalPic+totalPic/2 - 1) * (this.pointDiameter + this.pointDistance)  -
					this.pointDistance / 2 - this.pointRadius;
		} else { //����
			this.xStartPos = this.width - 
					(totalPic+totalPic/2) * (this.pointDiameter + this.pointDistance);
		}
		
		this.yPos = this.height / 2 - yPosOffset;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		this.width = w;
		this.height = h;
		
		this.pointRadius = 0; //Բ�İ뾶�����ݲ�ͬ�ֱ�������Ӧ
		this.pointDiameter = pointRadius * 2; //Բ��ֱ��
		this.pointDistance =w/25; //����Բ�ļ������
		
		yPosOffset = height/4 ;
		//��������
		refreshData(this.totalPic, this.currentPicPos);
	}
	
	/**
	 * �Ŵ󶯻�
	 * @return
	 */
/*	protected Animation toBigAnimation() {
		Animation anima = new ScaleAnimation(0.0f, 1.4f, 
				0.0f, 1.4f, 0.5f, 0.5f);
		anima.setDuration(1000);
		return anima;
	}*/
}
