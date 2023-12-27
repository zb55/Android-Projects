@Entry
@Component
struct Index {
  @State image_width: number = 200

  build() {
      Column() {
        Row(){
          Image($r('app.media.icon'))
            .width(this.image_width)
            .interpolation(ImageInterpolation.High)
        }
        .width('100%')
        .height(400)
        .justifyContent(FlexAlign.Center)
        Row(){
          Text($r('app.string.width_label'))
            .fontSize(20)
          TextInput({text: this.image_width.toString()})
            .height(40)
            .width(200)
            .backgroundColor(Color.White)
            .textAlign(TextAlign.Center)
            .type(InputType.Number)
            .onChange(value=>{
              this.image_width = parseInt(value)
            })
        }
        .width('100%')
        .padding({left:20, right:20})
        .justifyContent(FlexAlign.SpaceBetween)
        Divider()
          .width('90%')
          .height(6)
        Row(){
          Button('缩小')
            .type(ButtonType.Normal)
            .onClick(() => {
              if (this.image_width > 100) {
                this.image_width -= 10
              }

          })
          Button('放大')
            .type(ButtonType.Normal)
            .onClick(()=>{
              if (this.image_width < 300) {
                this.image_width += 10
              }
            })
        }
        .width('100%')
        .height(80)
        .justifyContent(FlexAlign.SpaceAround)
        Slider({
          min: 100,
          max: 300,
          value: this.image_width,
          step: 10,
          style: SliderStyle.OutSet,
          direction: Axis.Horizontal
        })
          .trackThickness(6)
          .showTips(true)
          .onChange(value=>{
            this.image_width = value;
          })

      }
      .width('100%')
      .height('100%')
      .padding(10)
  }
}