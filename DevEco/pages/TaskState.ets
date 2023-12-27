@Observed
class Task{
  static id: number = 1
  name: string = '任务' + (Task.id++).toString()
  finished: boolean = false
}

@Styles function card(){
  .width('100%')
  .padding(20)
  .borderRadius(15)
  .backgroundColor(Color.White)
}

@Entry
@Component
struct TaskState {
  @State totalTask: number = 0
  @State finishedTask: number = 0

  build() {
    Column({space:10}){
      TaskStatistics({finishedTask: this.finishedTask, totalTask: this.totalTask})
      TaskList({finishedTask: $finishedTask, totalTask: $totalTask})
    }
    .width('100%')
    .height('100%')
    .padding(10)
    .backgroundColor('#DDD')
    .justifyContent(FlexAlign.Start)
  }

}


@Component
struct TaskStatistics {
  @Prop finishedTask: number
  @Prop totalTask: number

  build() {
    Row(){
      Text('任务进度：')
        .fontSize(35)
        .fontWeight(FontWeight.Bold)
      Stack(){
        Progress({
          value:this.finishedTask,
          total:this.totalTask,
          type:ProgressType.Ring
        })
          .width(100)
        Row(){
          Text(this.finishedTask.toString())
            .fontSize(24)
            .fontColor('#38D')
          Text(' / ' + this.totalTask.toString())
            .fontSize(30)
        }
      }
    }
    .card()
  }
}

@Component
struct TaskList {
  @Link finishedTask:number
  @Link totalTask: number
  @State Tasks: Task[] = []

  handleChange(){
    this.totalTask = this.Tasks.length
    this.finishedTask = this.Tasks.filter(value=>value.finished).length
  }
  build() {
    Column(){
      Button('新增任务')
        .onClick(()=>{
          this.Tasks.push(new Task())
          this.handleChange()
        })
      List({space: 10}){
        ForEach(this.Tasks, (item: Task, index: number)=>{
          ListItem(){
            TaskItem({item: item, onHandleChange: this.handleChange.bind(this)})
          }
          .swipeAction({end: this.deleteButton(index)})
        })
      }
      .layoutWeight(1)
    }
  }
  @Builder deleteButton(index: number){
    Button(){
      Image($r('app.media.delete'))
        .width(20)
        .fillColor(Color.White)
    }
    .width(40)
    .height(40)
    .backgroundColor(Color.Red)
    .type(ButtonType.Circle)
    .margin(5)
    .onClick(()=>{
      this.Tasks.splice(index, 1)
      this.handleChange()
    })
  }
}

@Component
struct TaskItem {
  @ObjectLink item:Task
  onHandleChange: ()=>void
  build() {
    Row(){
      if (this.item.finished){
        Text(this.item.name)
          .fontSize(20)
          .decoration({type:TextDecorationType.LineThrough})
      }else{
        Text(this.item.name)
          .fontSize(25)
      }
      Checkbox()
        .select(this.item.finished)
        .onChange((value: boolean)=>{
          this.item.finished = value
          this.onHandleChange()
        })
    }
    .card()
    .justifyContent(FlexAlign.SpaceBetween)
  }
}