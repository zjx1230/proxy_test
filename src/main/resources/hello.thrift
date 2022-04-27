enum Operation {
  ADD = 1,
  SUBTRACT = 2,
  MULTIPLY = 3,
  DIVIDE = 4
}

struct Message {
  1: i32 num1,
  2: i32 num2,
  3: Operation op,
  4: string content,
}

service MessageService {

  void onReceiveMessage(1: Message message)

  void onReceiveMessageList(1: list<Message> messageList)

  void onReceiveStr(1: string message)
}

service HelloWorldService {
  void say(1: string content)
}