package ru.sitronics.tn.taskservice.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sitronics.tn.taskservice.dto.BpmsErrorMessageDto;
import ru.sitronics.tn.taskservice.dto.ProcessGroupDto;
import ru.sitronics.tn.taskservice.model.ProcessGroup;
import ru.sitronics.tn.taskservice.service.ProcessGroupService;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/process-group", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProcessGroupController {
    private static final Logger logger = LoggerFactory.getLogger(ProcessGroup.class);
    private static final String NEW_PROCESS_GROUP_LOG = "Creating new process group:{}";
    private static final String UPDATE_STATUS_LOG = "Updating process group status:{}";

    private final ProcessGroupService processGroupService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessGroupDto> createProcessGroup(@Valid @RequestBody ProcessGroupDto processGroupDto) {
        logger.info(NEW_PROCESS_GROUP_LOG, processGroupDto);
        final ProcessGroupDto response = processGroupService.createProcessGroup(processGroupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping(path = "/{processGroupId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateProcessGroupStatus(@PathVariable String processGroupId,
                                                         @RequestBody BpmsErrorMessageDto bpmsErrorMessageDto) {
        logger.info(UPDATE_STATUS_LOG, processGroupId);
        processGroupService.updateProcessGroupStatus(processGroupId, bpmsErrorMessageDto.getStatus(), bpmsErrorMessageDto.getErrorMessage());
        return ResponseEntity.noContent().build();
    }
}
