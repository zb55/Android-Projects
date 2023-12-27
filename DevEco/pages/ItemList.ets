import {HeadLine} from '../MyComponents/HeadLine'

class Item{
  public  image: number;
  public  name: string;
  public  price: number;
  public  discount: number;

  constructor(image: number, name:string, price: number, discount: number = 0) {
    this.image = image;
    this.name = name;
    this.price = price;
    this.discount = discount
  }
}

//公共样式
@Styles function mainStyle(){
  .width('100%')
  .height('100%')
  .backgroundColor('#EEE')
  .padding(20)
}

//专用样式,只能struct外部定义
@Extend(Text) function priceStyle(){
  .fontSize(25)
  .fontColor(Color.Red)
}

@Builder function CommodityItem(item: Item) {
  Row() {
    Text(item.image.toString())
      .width('40%')
      .fontSize(50)
      .textAlign(TextAlign.Center)
    Column() {
      Text(item.name)
        .fontSize(30)
        .fontWeight(FontWeight.Bold)
      if (item.discount) {
        Text('原价：￥' + item.price)
          .fontSize(15)
          .fontColor(Color.Grey)
          .decoration({type: TextDecorationType.LineThrough})
        Text('现价：￥' + (item.price - item.discount))
          .priceStyle()
        Text('折扣：￥' + item.discount)
          .priceStyle()
      } else {
        Text('￥' + item.price)
          .priceStyle()
      }
    }
    .width('100%')
    .height('100%')
    .alignItems(HorizontalAlign.Start)
  }
  .width('100%')
  .height(120)
  .borderRadius(20)
  .backgroundColor(Color.White)
  .padding(10)
}

@Entry
@Component
struct ItemForeach {
  @Styles mainStyle(){
    .width('100%')
    .height('100%')
    .backgroundColor('#EEE')
    .padding(20)
  }
  private items: Array<Item> = [
    new Item(1, 'A', 3000, 20),
    new Item(2, 'B', 5000),
    new Item(3, 'C', 3880, 20),
    new Item(4, 'D', 6000),
    new Item(5, 'E', 9000),
    new Item(6, 'F', 9000),
    new Item(7, 'G', 9000),
    new Item(8, 'H', 9000),
    new Item(9, 'I', 9000)
  ]
  build() {
    Column({space:10}) {
      // Row() {
      //   Text('商品列表')
      //     .fontSize(30)
      //     .fontWeight(FontWeight.Bolder)
      // }
      // .width('100%')
      HeadLine({head_text: '商品列表'})

      List({space: 10}){
        ForEach(this.items, (item: Item) => {
          ListItem(){
            // CommodityItem(item)//全局函数创建ListItem
            this.CommodityItem(item)//内部函数创建ListItem
          }
        })
      }
      .width('100%')
      .layoutWeight(1)
      .divider({})
    }
    // .width('100%')
    // .height('100%')
    // .backgroundColor('#EEE')
    // .padding(20)
    .mainStyle()//struct内外定义用法相同
  }
  @Builder CommodityItem(item: Item) {
  Row() {
    Text(item.image.toString())
      .width('40%')
      .fontSize(50)
      .textAlign(TextAlign.Center)
    Column() {
      Text(item.name)
        .fontSize(30)
        .fontWeight(FontWeight.Bold)
      if (item.discount) {
        Text('原价：￥' + item.price)
          .fontSize(15)
          .fontColor(Color.Grey)
          .decoration({type: TextDecorationType.LineThrough})
        Text('现价：￥' + (item.price - item.discount))
          .fontSize(25)
          .fontColor(Color.Red)
        Text('折扣：￥' + item.discount)
          .fontSize(25)
          .fontColor(Color.Red)
      } else {
        Text('￥' + item.price)
          .fontSize(25)
          .fontColor(Color.Red)
      }
    }
    .width('100%')
    .height('100%')
    .alignItems(HorizontalAlign.Start)
  }
  .width('100%')
  .height(120)
  .borderRadius(20)
  .backgroundColor(Color.White)
  .padding(10)
}
}