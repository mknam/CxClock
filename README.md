# CxClock
안드로이드 커스텀뷰를 이용한 시계 예제
- CxAnalogClock

## CxAnalogClock

### 목표
- SurfaceView를 사용한 아날로그 시계

### 구현
- Runnable 인터페이스를 구현하여 쓰레드를 생성한다
- 쓰레드는 1초주기로 화면을 갱신한다

### 사용예제

- 레이아웃에 다음과 같이 추가한다
```xml
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/background_dark">


    <com.example.cxclock.CxAnalogClock
        android:id="@+id/analogclock"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</RelativeLayout>
```

- Activity의 *onResume* 과 *onPause*를 오버라이드 하여
다음과 같이 CxAnalogClock 클래스의 **start()** 와 **stop()** 을 호출한다

> MainActivity.java
```java

	CxAnalogClock mClkView;

    @Override
    protected void onResume() {
        super.onResume();

        if(mClkView.isStop()) {
            mClkView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mClkView.stop();
    }
```

