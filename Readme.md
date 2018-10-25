# Music Player

### 동작 시현

![](/screenshot/musicplayer.gif)

* 안드로이드에서 제공하는 MediaPlayer 클래스를 활용하여 음악 플레이어를 구현
* 음악 목록을 보여주고 선택하여 음악을 들을 수 있고, 화면을 드래그하여 쉽게 음악을 전환할 수 있다.

#### 권한 설정

* 음악 파일에 접근하기 위해서 **READ 권한**이 필요하다.

* manifest.xml
````xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
````

* source code
````java
private static String[] permissions = {
    Manifest.permission.READ_EXTERNAL_STORAGE
};
````

#### 음악 정보 가져오기

* 음악 파일의 정보를 가져오기 위해 DAO를 정의한다.
* 음악 하나하나의 정보를 담을 그릇으로 Music 클래스를 정의하였고, Manager 클래스로 MusicLoader를 정의한다.

````java
public class Music {
    public String id;
    public String title;
    public String artist;
    public String albumid;
    public long duration;
    public Uri musicUri;
    public Uri albumartUri;
}
````
* ContentResolver를 생성하고, query를 보내 미디어 정보들을 가져온다.

````java
ContentResolver resolver = ctx.getContentResolver();
Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
String projections[] = {
    MediaStore.Audio.Media._ID,
    MediaStore.Audio.Media.TITLE,
    MediaStore.Audio.Media.ARTIST,
    MediaStore.Audio.Media.ALBUM_ID,
    MediaStore.Audio.Media.DURATION
};
Cursor cursor = resolver.query(musicUri, projections, null, null, null);
while (cursor.moveToNext()) {
    Music music = new Music(
        cursor.getString(cursor.getColumnIndex(projections[0])),
        cursor.getString(cursor.getColumnIndex(projections[1])),
        cursor.getString(cursor.getColumnIndex(projections[2])),
        cursor.getString(cursor.getColumnIndex(projections[3])),
        cursor.getLong(cursor.getColumnIndex(projections[4]))
    );
    music.update();
    list.add(music);
}
````

#### MusicPlayer

* 원하는 기능들만 쉽게 사용할 수 있도록 만든 Manager 클래스
* MusicPlayer는 안드로이드에서 제공되는 MediaPlayer 클래스를 사용한다.

````java
public class MusicPlayer {
    private static MediaPlayer mediaPlayer;

    public static void set(Context ctx, Uri uri);
    public static void play();
    public static void stop();
    public void release();
    public int duration();
    public void seekTo(int msec);
    public int getMax();
}
````

#### Player

* MusicPlayer와 View를 핸들링하는 클래스
* Player는 ViewPager를 사용하며, PagerAdapter를 상속하여 PlayerAdapter를 정의해야 한다.
* PlayerAdapter는 MusicLoader에서 가져온 미디어 정보들을 리스트로 받아서 활용한다.

````java
public class PlayerAdapter extends PagerAdapter {
    List<Music> list;

    public PlayerAdapter(List<Music> list) { this.list = list;}

    @Override
    public int getCount() { return list.size(); }

    // View inflation fucntion
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {}

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // 컨테이너에 추가되었던 뷰를 삭제
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
````