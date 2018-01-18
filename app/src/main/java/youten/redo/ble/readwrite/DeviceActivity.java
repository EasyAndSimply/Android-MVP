/*
 * Copyright (C) 2013 youten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package youten.redo.ble.readwrite;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.util.UUID;

import youten.redo.ble.util.BleUtil;
import youten.redo.ble.util.BleUuid;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * BLEデバイスへのconnect・Service
 * Discoveryを実施し、Characteristicsのread/writeをハンドリングするActivity
 */
public class DeviceActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "BLEDevice";

    public static final String EXTRA_BLUETOOTH_DEVICE = "BT_DEVICE";
    private BluetoothAdapter mBTAdapter;
    private BluetoothDevice mDevice;
    private BluetoothGatt mConnGatt;
    private int mStatus;

    private Button mReadManufacturerNameButton;
    private Button mReadSerialNumberButton;
    private Button mWriteAlertLevelButton;
    public static final String char1 = "05000400-10AE-4C36-9A27-96BCD4C7F835";
    public static final String char2 = "05000500-10AE-4C36-9A27-96BCD4C7F835";
    public static final String char3 = "05000600-10AE-4C36-9A27-96BCD4C7F835";
    public static final String servises = "05000000-10AE-4C36-9A27-96BCD4C7F835";

    private static final UUID nom11 = UUID.fromString("05000400-10AE-4C36-9A27-96BCD4C7F835");
    private static final UUID nom12 = UUID.fromString("05000500-10AE-4C36-9A27-96BCD4C7F835");
    private static final UUID nom13 = UUID.fromString("05000600-10AE-4C36-9A27-96BCD4C7F835");
    private TextView num1;
    private TextView num2;
    private TextView num3;
    private Button start;

    private final BluetoothGattCallback mGattcallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                            int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mStatus = newState;
                mConnGatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mStatus = newState;
                runOnUiThread(new Runnable() {
                    public void run() {
                        mReadManufacturerNameButton.setEnabled(false);
                        mReadSerialNumberButton.setEnabled(false);
                        mWriteAlertLevelButton.setEnabled(false);
                    }

                });
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            for (BluetoothGattService service : gatt.getServices()) {
                if ((service == null) || (service.getUuid() == null)) {
                    continue;
                }


                if (BleUuid.SERVICE_DEVICE_INFORMATION.equalsIgnoreCase(service
                        .getUuid().toString())) {
                    mReadManufacturerNameButton.setTag(service.getCharacteristic(UUID.fromString(BleUuid.CHAR_MANUFACTURER_NAME_STRING)));
                    mReadSerialNumberButton.setTag(service.getCharacteristic(UUID.fromString(BleUuid.CHAR_SERIAL_NUMBEAR_STRING)));
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mReadManufacturerNameButton.setEnabled(true);
                            mReadSerialNumberButton.setEnabled(true);
                        }
                    });
                }
                if (BleUuid.SERVICE_IMMEDIATE_ALERT.equalsIgnoreCase(service
                        .getUuid().toString())) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mWriteAlertLevelButton.setEnabled(true);
                        }

                    });
                    mWriteAlertLevelButton.setTag(service
                            .getCharacteristic(UUID
                                    .fromString(BleUuid.CHAR_ALERT_LEVEL)));
                }

                if (servises.equalsIgnoreCase(service.getUuid().toString())) {
                    BluetoothGattCharacteristic ch = service.getCharacteristic(UUID.fromString(char1));
                    BluetoothGattCharacteristic ch2 = service.getCharacteristic(UUID.fromString(char2));
                    BluetoothGattCharacteristic ch3 = service.getCharacteristic(UUID.fromString(char3));
                    mConnGatt.readCharacteristic(ch);
                    mConnGatt.readCharacteristic(ch2);
                    mConnGatt.readCharacteristic(ch3);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    mWriteAlertLevelButton.setEnabled(true);
                                }

                            });


                }

//                    service.getCharacteristic(UUID.fromString(nom1));
//                            service.getCharacteristic(UUID.fromString(nom2));
//                            service.getCharacteristic(UUID.fromString(nom3));
            }

            runOnUiThread(new Runnable() {
                public void run() {
                    setProgressBarIndeterminateVisibility(false);
                }
            });

            runOnUiThread(new Runnable() {
                public void run() {
                    setProgressBarIndeterminateVisibility(false);
                }
            });
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (BleUuid.CHAR_MANUFACTURER_NAME_STRING.equalsIgnoreCase(characteristic.getUuid().toString())) {
                    final String name = characteristic.getStringValue(0);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            mReadManufacturerNameButton.setText(name);
                            setProgressBarIndeterminateVisibility(false);
                        }
                    });
                } else if (BleUuid.CHAR_SERIAL_NUMBEAR_STRING.equalsIgnoreCase(characteristic.getUuid().toString())) {
                    final String name = characteristic.getStringValue(0);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            mReadSerialNumberButton.setText(name);
                            setProgressBarIndeterminateVisibility(false);
                        }
                    });
                }


                    final byte[] name = characteristic.getValue();
                    float f = ByteBuffer.wrap(name).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                    String formattedDouble = new DecimalFormat("#0.0").format(f);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            num1.setText(formattedDouble);
                            Log.i(TAG, "Found Characteristic: " + characteristic.getUuid().toString());
                            setProgressBarIndeterminateVisibility(false);
                        }
                    });

                    final byte[] name2 = characteristic.getValue();
                    float f2 = ByteBuffer.wrap(name2).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                    String formattedDouble2 = new DecimalFormat("#0.0").format(f2);

                    runOnUiThread(new Runnable() {
                        public void run() {

                            num2.setText(formattedDouble2);
                            setProgressBarIndeterminateVisibility(false);
                        }
                    });

                    final byte[] name3 = characteristic.getValue();
                    float f3 = ByteBuffer.wrap(name3).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                    String formattedDouble3 = new DecimalFormat("#0.0").format(f3);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            num3.setText(formattedDouble3);
                            setProgressBarIndeterminateVisibility(false);
                        }
                    });
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic, int status) {

            runOnUiThread(new Runnable() {
                public void run() {
                    setProgressBarIndeterminateVisibility(false);
                }

                ;
            });
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_device);

        // state
        mStatus = BluetoothProfile.STATE_DISCONNECTED;
        mReadManufacturerNameButton = (Button) findViewById(R.id.read_manufacturer_name_button);
        mReadManufacturerNameButton.setOnClickListener(this);
        mReadSerialNumberButton = (Button) findViewById(R.id.read_serial_number_button);
        mReadSerialNumberButton.setOnClickListener(this);
        mWriteAlertLevelButton = (Button) findViewById(R.id.write_alert_level_button);
        mWriteAlertLevelButton.setOnClickListener(this);
        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        num3 = (TextView) findViewById(R.id.num3);
        start = (Button) findViewById(R.id.start);
    }

    @Override
    protected void onResume() {
        super.onResume();

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnGatt != null) {
            if ((mStatus != BluetoothProfile.STATE_DISCONNECTING)
                    && (mStatus != BluetoothProfile.STATE_DISCONNECTED)) {
                mConnGatt.disconnect();
            }
            mConnGatt.close();
            mConnGatt = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_manufacturer_name_button) {
            if ((v.getTag() != null)
                    && (v.getTag() instanceof BluetoothGattCharacteristic)) {
                BluetoothGattCharacteristic ch = (BluetoothGattCharacteristic) v
                        .getTag();
                if (mConnGatt.readCharacteristic(ch)) {
                    setProgressBarIndeterminateVisibility(true);
                }
            }
        } else if (v.getId() == R.id.read_serial_number_button) {
            if ((v.getTag() != null)
                    && (v.getTag() instanceof BluetoothGattCharacteristic)) {
                BluetoothGattCharacteristic ch = (BluetoothGattCharacteristic) v.getTag();
                if (mConnGatt.readCharacteristic(ch)) {
                    setProgressBarIndeterminateVisibility(true);
                }
            }

        } else if (v.getId() == R.id.write_alert_level_button) {
            if ((v.getTag() != null)
                    && (v.getTag() instanceof BluetoothGattCharacteristic)) {
                BluetoothGattCharacteristic ch = (BluetoothGattCharacteristic) v
                        .getTag();
                ch.setValue(new byte[]{(byte) 0x03});
                if (mConnGatt.writeCharacteristic(ch)) {
                    setProgressBarIndeterminateVisibility(true);
                }
            }
        }
    }

    private void init() {
        // BLE check
        if (!BleUtil.isBLESupported(this)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT)
                    .show();
            finish();
            return;
        }

        // BT check
        BluetoothManager manager = BleUtil.getManager(this);
        if (manager != null) {
            mBTAdapter = manager.getAdapter();
        }
        if (mBTAdapter == null) {
            Toast.makeText(this, R.string.bt_unavailable, Toast.LENGTH_SHORT)
                    .show();
            finish();
            return;
        }

        // check BluetoothDevice
        if (mDevice == null) {
            mDevice = getBTDeviceExtra();
            if (mDevice == null) {
                finish();
                return;
            }
        }

        // button disable
        mReadManufacturerNameButton.setEnabled(false);
        mReadSerialNumberButton.setEnabled(false);
        mWriteAlertLevelButton.setEnabled(false);

        // connect to Gatt
        if ((mConnGatt == null)
                && (mStatus == BluetoothProfile.STATE_DISCONNECTED)) {
            // try to connect
            mConnGatt = mDevice.connectGatt(this, false, mGattcallback);
            mStatus = BluetoothProfile.STATE_CONNECTING;
        } else {
            if (mConnGatt != null) {
                // re-connect and re-discover Services
                mConnGatt.connect();
                mConnGatt.discoverServices();
            } else {
                Log.e(TAG, "state error");
                finish();
                return;
            }
        }
        setProgressBarIndeterminateVisibility(true);
    }

    private BluetoothDevice getBTDeviceExtra() {
        Intent intent = getIntent();
        if (intent == null) {
            return null;
        }

        Bundle extras = intent.getExtras();
        if (extras == null) {
            return null;
        }

        return extras.getParcelable(EXTRA_BLUETOOTH_DEVICE);
    }

}
