OCR: Optical Character Recognition

intent-filter:   passport:
com.xdja.zdsb.passport.action

idcard: 
com.xdja.zdsb.sfzsb.action

driverLicense:
com.xdja.zdsb.sfzsb.action

car plate number:
com.xdja.zdsb.cpsb.action

调用返回： 
// 通过调用data.getStringExtra("data"); 可以得到相应的数据。 其中车牌号码是可以直接通过“number” 获得。
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAR_PLATE_REQUEST:
                    String number = null;
                    try{
                        result = data.getStringExtra("data");
                        number = data.getStringExtra("number");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    Zzlog.out(TAG, "number = " + number);
                    break;
                case ID_CARD_REQUEST:
                case DRIVER_LICENSE_REQUEST:
                case PASSPORT_REQUEST:
                    String result = null;
                    try{
                        result = data.getStringExtra("data");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    Zzlog.out(TAG, "DRIVER_LICENSE_REQUEST number = " + result);

                    break;
                default:
                    Zzlog.out(TAG, "onActivityResult default: requestCode + " + requestCode
                             + ", resultCode = " + resultCode + ", data = " + data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }