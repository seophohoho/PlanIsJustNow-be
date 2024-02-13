package com.planisjustnow.service;

import com.planisjustnow.data.dto.ChoicePetDto;
import com.planisjustnow.data.dto.UserInfoDto;
import com.planisjustnow.data.entity.NatureEntity;
import com.planisjustnow.data.entity.PetEntity;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.entity.UserPetEntity;
import com.planisjustnow.data.repository.NatureRepository;
import com.planisjustnow.data.repository.PetRepository;
import com.planisjustnow.data.repository.UserPetRepository;
import com.planisjustnow.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Service
public class UserPetService {
    static int natureListCount = 2;
    static int maxFriendShip = 15000;
    static int minFriendship = 10000;
    @Autowired
    private UserPetRepository userPetRepository;
    @Autowired
    private NatureRepository natureRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;
    @Transactional
    public String setUserPet(ChoicePetDto choicePetDto){
        Integer natureId = getRandomNature();
        int maxFriendship = getRandomMaxFriendship();

        UserEntity entity1 = userRepository.findByEmail(choicePetDto.getEmail());
        PetEntity entity2 = petRepository.findByPetId(choicePetDto.getSpecies());
        NatureEntity entity3 = natureRepository.findByNatureId(natureId);
        UserPetEntity userPetEntity = new UserPetEntity();
        try{
            userPetEntity.setUserId(entity1);
            userPetEntity.setPetId(entity2);
            userPetEntity.setNatureId(entity3);
            userPetEntity.setPetName(choicePetDto.getNickname());
            userPetEntity.setMaxFriendship(maxFriendship);
            userPetEntity.setCurrentFriendship(0);
            userPetEntity.setRunWayCount(0);
            userPetRepository.save(userPetEntity);
        }
        catch(Exception e){
            return "fail:Unexpected error";
        }
        return "success";
    }
    private Integer getRandomNature(){
        SecureRandom random = new SecureRandom();
        return random.nextInt(natureListCount) + 0;
    }
    private int getRandomMaxFriendship(){
        SecureRandom random = new SecureRandom();
        return random.nextInt(maxFriendShip) + minFriendship;
    }
    @Transactional
    public String isHasPet(UserInfoDto userInfoDto){
        try {
            List<UserPetEntity> userPetList = findUserPetInfo(userInfoDto.getEmail());
            System.out.println(userPetList);
            if(userPetList.size() > 0){
                return "success:has";
            }
            else{
                return "success:nothing";
            }
        }catch(NullPointerException e){
            return "fail:Unexpected error";
        }
    }
    public List<UserPetEntity> findUserPetInfo(String email){
        return userPetRepository.findAllByUserIdEmail(email);
    }
}
