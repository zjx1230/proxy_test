/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.example.proxy;

import java.io.IOException;
import java.net.ServerSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.example.thrift.MessageService;

/**
 * TODO
 *
 * @author zjx
 * @since 2022/4/27 上午11:24
 */
public class MessageServer {

  public static void main(String[] args) throws IOException, TTransportException {
//    ServerSocket serverSocket = new ServerSocket(6669);
//    TServerSocket serverTransport = new TServerSocket(serverSocket);
//    MessageService.Processor processor =
//        new MessageService.Processor<MessageService.Iface>(new MessageServiceImpl());
//    TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
//    server.serve();

    ServerSocket serverSocket = new ServerSocket(30000);
    TServerSocket serverTransport = new TServerSocket(serverSocket);
    MessageService.Processor processor =
        new MessageService.Processor<MessageService.Iface>(new MessageServiceImpl());

    TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory();
    TSimpleServer.Args tArgs = new TSimpleServer.Args(serverTransport);
    tArgs.processor(processor);
    tArgs.protocolFactory(protocolFactory);

    // 简单的单线程服务模型 一般用于测试
    TServer tServer = new TSimpleServer(tArgs);
    System.out.println("Running Simple Server");
    tServer.serve();
  }
}
