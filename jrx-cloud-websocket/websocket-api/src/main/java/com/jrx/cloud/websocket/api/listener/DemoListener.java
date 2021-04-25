package com.jrx.cloud.websocket.api.listener;

import com.jrx.cloud.assembly.constant.RabbitMqConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author x
 * @version 1.0  2021/4/25
 */
@Slf4j
@Component
public class DemoListener {

//    @RabbitListener(queues = {RabbitMqConstants.QUEUE_SYS_INFORMATION})
    public void processNotice(String jsonData, Channel channel, Message message) throws IOException {
        try {
            log.info("### DemoListener.processNotice receive data: {} ###", jsonData);

//            GwCmdNoticeDTO noticeDTO = FastJsonUtil.jsonToObject(GwCmdNoticeDTO.class, jsonData);
//
//            String groupKey = WebsocketConstants.getGroupKey(noticeDTO.getGwSn(), noticeDTO.getDevId() == null ? null : String.valueOf(noticeDTO.getDevId()));
//            if (!StringUtils.isEmpty(groupKey)) {
//                CmdExecutedResultDTO cmdExecutedResultDTO = CmdExecutedResultDTO.builder()
//                        .flag(noticeDTO.getRespFlag())
//                        .devId(noticeDTO.getDevId())
//                        .msg(noticeDTO.getRespMsg())
//                        .ts(noticeDTO.getTs())
//                        .build();
//
//                ChannelGroup group = WebsocketConstants.CHANNEL_GROUP_MAP.get(groupKey);
//                if (group != null) {
//                    group.writeAndFlush(new TextWebSocketFrame(FastJsonUtil.objectToJson(new ResultDTO<>(cmdExecutedResultDTO, ResultDataTypeEnum.CMD_EXECUTE_RESULT))));
//                }
//            }

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("### DemoListener.processNotice process error: {} ###", e.getMessage(), e);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
