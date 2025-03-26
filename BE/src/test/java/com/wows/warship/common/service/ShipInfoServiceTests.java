package com.wows.warship.common.service;

import com.wows.warship.common.domain.ShipInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
public class ShipInfoServiceTests {

    @Autowired
    private ShipInfoService shipInfoService;

    @DisplayName("배 정보를 확인 할 수 있다.")
    @Test
    public void getShipInfoTest(){
        //given
        //when
        Map<Long, ShipInfo> shipInfo = shipInfoService.getShipInfo(); //780

        //then
        assertEquals(779, shipInfo.keySet().size());
        assertEquals("Atlanta", shipInfo.get(4288591856l).getShipName());
        assertEquals("Jäger", shipInfo.get(3750638896l).getShipName());
    }

}
