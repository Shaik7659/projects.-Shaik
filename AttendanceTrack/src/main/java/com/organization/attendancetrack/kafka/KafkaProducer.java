package com.organization.attendancetrack.kafka;

import com.organization.attendancetrack.base.ConstantField;
import com.organization.attendancetrack.model.AttendanceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, AttendanceRecord> kafkaTemplate;
	public void produce(AttendanceRecord record) {
		kafkaTemplate.send(ConstantField.Kafka_Topic, record);
	}
}