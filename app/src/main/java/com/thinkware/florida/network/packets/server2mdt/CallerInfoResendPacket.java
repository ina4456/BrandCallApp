package com.thinkware.florida.network.packets.server2mdt;

import com.thinkware.florida.network.packets.Packets;
import com.thinkware.florida.network.packets.ResponsePacket;

/**
 * Created by zic325 on 2016. 9. 8..
 * 고객정보 재전송 (GT-1A12) 186 Byte (패킷정의서의 250이 틀린 것임)

 * Edit by sbkwon 2017. 9. 19
 * 복지콜 추가. 따라서 규격서에 나온대로 +64 byte 사용하게 변경
 *
 * Server -> MDT
 */
public class CallerInfoResendPacket extends ResponsePacket {

	private int corporationCode; // 법인코드 (2)
	private int carId; // Car ID (2)
	private Packets.OrderKind orderKind; // 배차구분 (1)
	private String callReceiptDate; // 콜접수일자(ex : 2009-01-23) (11)
	private int callNumber; // 콜번호 (2)
	private float longitude; // 고객 경도 (4)
	private float latitude; // 고객 위도 (4)
	private String callerPhone; // 고객 연락처 (13)
	private String place; // 탑승지 (41)
	private String placeExplanation; // 탑승지 설명 (101)
	private int allocBoundary; // 배차범위 (2)
	private int orderCount; // 배차횟수 (1)
	private String callerName; // 고객이름 (11)
	private String errorCode; // 장애코드 (11)
	private String destination; // 목적지 (41)
	private boolean isWheelChair; // 휠체어여부 (1)

	private boolean isCallForDisabledPerson; //복지콜 여부
	final static int BYTE_LENGTH_FOR_DISABLED_PERSON = 250;

	public CallerInfoResendPacket(byte[] bytes) {
		super(bytes);
		if(bytes.length == BYTE_LENGTH_FOR_DISABLED_PERSON){
			isCallForDisabledPerson = true;
		} else {
			isCallForDisabledPerson = false;
		}
	}

	public int getCorporationCode() {
		return corporationCode;
	}

	public int getCarId() {
		return carId;
	}

	public Packets.OrderKind getOrderKind() {
		return orderKind;
	}

	public String getCallReceiptDate() {
		return callReceiptDate;
	}

	public int getCallNumber() {
		return callNumber;
	}

	public float getLongitude() {
		return longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public String getCallerPhone() {
		return callerPhone;
	}

	public String getPlace() {
		return place;
	}

	public String getPlaceExplanation() {
		return placeExplanation;
	}

	public int getAllocBoundary() {
		return allocBoundary;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public String getCallerName() {
		return callerName;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getDestination() {
		return destination;
	}

	public boolean isWheelChair() {
		return isWheelChair;
	}

	public boolean isCallForDisabledPerson() {
		return isCallForDisabledPerson;
	}

	@Override
	public void parse(byte[] buffers) {
		super.parse(buffers);
		corporationCode = readInt(2);
		carId = readInt(2);
		int order = readInt(1);
		if (Packets.OrderKind.Normal.value == order) {
			orderKind = Packets.OrderKind.Normal;
		} else if (Packets.OrderKind.Wait.value == order) {
			orderKind = Packets.OrderKind.Wait;
		} else if (Packets.OrderKind.Forced.value == order) {
			orderKind = Packets.OrderKind.Forced;
		} else if (Packets.OrderKind.Manual.value == order) {
			orderKind = Packets.OrderKind.Manual;
		} else if (Packets.OrderKind.WaitOrder.value == order) {
			orderKind = Packets.OrderKind.WaitOrder;
		} else if (Packets.OrderKind.WaitOrderTwoWay.value == order) {
			orderKind = Packets.OrderKind.WaitOrderTwoWay;
		} else if (Packets.OrderKind.GetOnOrder.value == order) {
			orderKind = Packets.OrderKind.GetOnOrder;
		} else if (Packets.OrderKind.Mobile.value == order) {
			orderKind = Packets.OrderKind.Mobile;
		}
		callReceiptDate = readString(11);
		callNumber = readInt(2);
		longitude = readFloat(4);
		latitude = readFloat(4);
		callerPhone = readString(13);
		place = readString(41);
		placeExplanation = readString(101);
		allocBoundary = readInt(2);
		orderCount = readInt(1);
		callerName = readString(11);
		errorCode = readString(11);
		destination = readString(41);
		int wheelChair = readInt(1);
		isWheelChair = wheelChair == 0x02;
	}

	@Override
	public String toString() {
		return "고객정보재전송 (0x" + Integer.toHexString(messageType) + ") " +
				"corporationCode=" + corporationCode +
				", carId=" + carId +
				", orderKind=" + orderKind +
				", callReceiptDate='" + callReceiptDate + '\'' +
				", callNumber=" + callNumber +
				", longitude=" + longitude +
				", latitude=" + latitude +
				", callerPhone='" + callerPhone + '\'' +
				", place='" + place + '\'' +
				", placeExplanation='" + placeExplanation + '\'' +
				", allocBoundary=" + allocBoundary +
				", orderCount=" + orderCount +
				", callerName='" + callerName + '\'' +
				", errorCode='" + errorCode + '\'' +
				", destination='" + destination + '\'' +
				", isWheelChair=" + isWheelChair +
				'}';
	}
}