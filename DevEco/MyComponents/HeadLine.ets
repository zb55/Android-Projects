
@Component
export  struct HeadLine {
  public head_text = "商品列表"
  build(){
    Column(){
      Row(){
        Image($r('app.media.return'))
          .height(50)
        Text(this.head_text)
          .fontSize(30)
          .textAlign(TextAlign.Start)
          .width('60%')
        Image($r('app.media.refresh'))
          .height(50)
      }
      .width('100%')
      .justifyContent(FlexAlign.SpaceBetween)
    }
    .width('100%')
  }
}