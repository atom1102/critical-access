package org.test.client.mcopclient.controller.events;

import android.content.Intent;
import android.util.Log;

import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.controller.MCOPCallManager;

import static org.test.client.mcopclient.ConstantsMCOP.ERROR_CODE_DEFAULT;

public class CallEvent implements EventListener {
    private final static String TAG = CallEvent.class.getCanonicalName();

    @Override
    public void handleEvent(Intent action) {
        int eventTypeInt = action.getIntExtra(ConstantsMCOP.CallEventExtras.EVENT_TYPE, ERROR_CODE_DEFAULT);
        ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum eventTypeCall = null;
        String sessionID = action.getStringExtra(ConstantsMCOP.CallEventExtras.SESSION_ID);
        String callerID = action.getStringExtra(ConstantsMCOP.CallEventExtras.CALLER_USERID);
        int callType = action.getIntExtra(ConstantsMCOP.CallEventExtras.CALL_TYPE, ERROR_CODE_DEFAULT);
        int updateCallType = action.getIntExtra(ConstantsMCOP.CallEventExtras.CALL_TYPE, ERROR_CODE_DEFAULT);
        MCOPCallManager.mapSessionToCall(sessionID, callerID);
        if (eventTypeInt != ERROR_CODE_DEFAULT &&
                (eventTypeCall = ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum.fromInt(eventTypeInt)) != null) {
            switch (eventTypeCall) {
                case NONE:
                    break;
                case INCOMING:
                    Log.d(TAG, "STATE: INCOMING");
                    String stringError = action.getStringExtra(ConstantsMCOP.CallEventExtras.ERROR_STRING);

                    if (org.test.client.mcopclient.model.calls.CallEvent.validationCallType(callType) == org.test.client.mcopclient.model.calls.CallEvent.CallTypeValidEnum.AudioWithFloorCtrlPrivateEmergency) {
                        Log.d(TAG, "Prearranged Emergency Group Call");
                        //MCOPCallManager.startERState();
                    } else if (org.test.client.mcopclient.model.calls.CallEvent.validationCallType(callType) == org.test.client.mcopclient.model.calls.CallEvent.CallTypeValidEnum.AudioWithFloorCtrlPrivateEmergency) {
                        Log.d(TAG, "Private Emergency Call");
                        //MCOPCallManager.startERState();
                    }

                    break;
                case RINGING:
                    Log.d(TAG, "STATE: RINGING");
                    break;
                case INPROGRESS:
                    Log.d(TAG, "STATE: INPROGRESS");

                    break;
                case CONNECTED:
                    Log.d(TAG, "STATE: CONNECTED");
                    if (org.test.client.mcopclient.model.calls.CallEvent.validationCallType(callType) == org.test.client.mcopclient.model.calls.CallEvent.CallTypeValidEnum.AudioWithFloorCtrlPrearrangedGroupEmergency) {
                        Log.d(TAG, "Prearranged Emergency Group Call");
                        //MCOPCallManager.startERState();
                    } else if (org.test.client.mcopclient.model.calls.CallEvent.validationCallType(callType) == org.test.client.mcopclient.model.calls.CallEvent.CallTypeValidEnum.AudioWithFloorCtrlPrivateEmergency) {
                        Log.d(TAG, "Private Emergency Call");
                        //MCOPCallManager.startERState();
                    }
                    break;
                case TERMINATED:
                    Log.d(TAG, "STATE: TERMINATED");
                    sessionID = action.getStringExtra(ConstantsMCOP.CallEventExtras.SESSION_ID);
                    break;
                case ERROR:
                    Log.e(TAG, "STATE: ERROR");
                    if (action.getIntExtra(ConstantsMCOP.CallEventExtras.ERROR_CODE, ERROR_CODE_DEFAULT) != ERROR_CODE_DEFAULT) {
                        // Error in callEvent
                        stringError = action.getStringExtra(ConstantsMCOP.CallEventExtras.ERROR_STRING);
                    }
                    break;
                case UPDATE:
                    Log.d(TAG, "STATE: UPDATE");
                    break;
                default:
                    break;
            }
        }
    }
}
