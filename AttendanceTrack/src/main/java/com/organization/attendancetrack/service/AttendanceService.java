package com.organization.attendancetrack.service;

import com.organization.attendancetrack.base.ConstantField;
import com.organization.attendancetrack.entity.SwipeEventEntity;
import com.organization.attendancetrack.kafka.KafkaProducer;
import com.organization.attendancetrack.model.AttendanceRecord;
import com.organization.attendancetrack.model.SwipeEvent;
import com.organization.attendancetrack.repository.SwipeEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AttendanceService {

    @Autowired
    private SwipeEventRepository swipeEventRepository;
    @Autowired
    private KafkaProducer kafkaProducer;
    private Logger logger= LoggerFactory.getLogger(AttendanceService.class);
    public void processSwipeEvent(SwipeEvent swipeEvent) {
        LocalDateTime officeTimeIn,officeTimeOut;
        String swipeType;
        if(swipeEvent.swipeType().toUpperCase().equals(ConstantField.IN)){
            officeTimeIn=LocalDateTime.now();
            officeTimeOut=null;
            swipeType=ConstantField.IN;
        }else{
            officeTimeIn=null;
            officeTimeOut=LocalDateTime.now();
            swipeType=ConstantField.OUT;
        }
        AttendanceRecord attendanceRecord=new AttendanceRecord(swipeEvent.employeeId(),swipeType,officeTimeIn,officeTimeOut);
        kafkaProducer.produce(attendanceRecord);
        logger.info("Send message to Kafka Producer");
    }

    public String getTotalHoursInOffice(Long employeeId) {
        Optional<SwipeEventEntity> event=swipeEventRepository.findByEmployeeIdAndOfficeIn(employeeId, LocalDate.now());
        return  event.isPresent()?"Employee Id- "+employeeId+  "Office Coming Time -"+event.get().getOfficeIn()+"  Last Out Office Time - "+event.get().getOfficeOut()+"  Total Hour Spent in office -"+event.get().getStayInOffice()+"  Office Availability -"+event.get().getAvailablityInOffice():"Employee Id "+employeeId+" today Absent";
    }


}
