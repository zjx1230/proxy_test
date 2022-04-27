package org.example.proxy;

import java.util.List;
import org.apache.thrift.TException;
import org.example.thrift.Message;

/**
 * TODO
 *
 * @author zjx
 * @since 2022/4/27 上午9:38
 */
public interface RpcProtocol {

  void sendMessage(Message message) throws TException;

  void sendMessageList(List<Message> messageList) throws TException;

  void sendMessage(String message) throws TException;

  void handleLocalMessage(String message);
}
