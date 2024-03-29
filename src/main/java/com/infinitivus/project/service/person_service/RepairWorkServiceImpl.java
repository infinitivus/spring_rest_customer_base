package com.infinitivus.project.service.person_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.infinitivus.project.entity.person.MobileHome;
import com.infinitivus.project.entity.person.RepairWork;
import com.infinitivus.project.repository.person_repository.IMobileHomeRepository;
import com.infinitivus.project.repository.person_repository.IRepairWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RepairWorkServiceImpl implements IRepairWorkService {

    @Autowired
    private IRepairWorkRepository repairWorkRepository;

    @Autowired
    private IMobileHomeRepository mobileHomeRepository;

    @Override
    public List<RepairWork> allRepairWork() {
        return repairWorkRepository.findAll();
    }

    @Override
    public RepairWork saveRepairWork(RepairWork repairWork, Integer homeId) {
        getHome(homeId).addRepairWorkToMobileHome(repairWork);
        return repairWorkRepository.save(repairWork);
    }

    @Override
    public RepairWork getOneWork(Integer id){
        return repairWorkRepository.findById(id).get();
    }

    @Override
    public RepairWork updateWork(Integer id,JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        RepairWork repairWork = getOneWork(id);
        RepairWork repairWorkPatched=applyPatchToRepairWork(patch,repairWork);
        return  repairWorkRepository.save(repairWorkPatched);
    }

    @Override
    public List<RepairWork> getRepairWork(Integer homeId) {
        return getHome(homeId).getRepairWorkList();
    }

    @Override
    public void deleteRepairWork(Integer id) {
        RepairWork repairWork = repairWorkRepository.getById(id);
        List<RepairWork> listWork = getRepairWork(repairWork.getMobileHomeRepair().getId());
        listWork.remove(repairWork);
        repairWorkRepository.deleteById(id);
    }

    public RepairWork applyPatchToRepairWork(JsonPatch patch, RepairWork repairWork) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(repairWork, JsonNode.class));
        return objectMapper.treeToValue(patched, RepairWork.class);
    }

    private MobileHome getHome(Integer id) {
        return mobileHomeRepository.findById(id).get();
    }
}
