# App_phone
This is Mobile side Application

## クラスの説明
- FirstMenu
  - 最初に表示されるメニュー
  - レコード機能とコース管理は未実装
  
- MainActivity
  -ロゴ表示
  
- ModeSelect
  - VSモードかペースキーパーモードかを選択
  
- SetCourse
  - コースファイルがあるならコース選択
  - 無ければコース作成
  
- MethodSelect
  - インターバル走かペース走かを選択
  - ペースキーパーのみの画面
  - ペースキーパーの設定を設定クラスに渡すにはこのクラスの
  onClickメソッドを使う
  
- SpeedSelect
  - 走る速さを選択
  
- SettingConfirm
  - 設定確認画面(ペースキーパー)
  
- VsStart
  - 設定確認画面(VSモード)
  
- RunRecorder
  - 走行中に記録を保存するクラス
  - 実機デバッグ未実施
  
- ShowRecord
  - 走った後、記録を表示させるクラス
  - デバッグ未実施
  
- Setting
  - モードやファイル名など各機能を保存するためのもの

- recordLatLng
  - 走行中に取得した緯経度を配列に格納し、ファイルに書き込む際に利用するクラス
