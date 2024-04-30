package com.planisjustnow.service;

import com.planisjustnow.data.dto.ChoicePetDto;
import com.planisjustnow.data.dto.PetSignUpDto;
import com.planisjustnow.data.entity.*;
import com.planisjustnow.data.repository.*;
import com.planisjustnow.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;

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
    @Autowired
    private AllPetRepository allPetRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Transactional
    public String petSignup(String userId, PetSignUpDto petSignUpDto){
        Integer natureId = getRandomNature();
        int maxFriendship = getRandomMaxFriendship();

        List<UserPetEntity> userPets = findUserPetInfo(userId);
        if(userPets.size() > 0){
            for(UserPetEntity userPet: userPets){
                userPet.setLastChoice(0);
            }
        }

        UserEntity entity1 = userRepository.findByEmail(userId);
        PetEntity entity2 = petRepository.findByPetId(petSignUpDto.getSpecies());
        NatureEntity entity3 = natureRepository.findByNatureId(natureId);
        UserPetEntity userPetEntity = new UserPetEntity();
        try{
            userPetEntity.setUserId(entity1);
            userPetEntity.setPetId(entity2);
            userPetEntity.setNatureId(entity3);
            userPetEntity.setPetName(petSignUpDto.getNickname());
            userPetEntity.setMaxFriendship(maxFriendship);
            userPetEntity.setCurrentFriendship(0);
            userPetEntity.setRunWayCount(0);
            userPetEntity.setLastChoice(1);
            userPetRepository.save(userPetEntity);
        }
        catch(Exception e){
            return "fail:Unexpected error";
        }
        return "success";
    }
    public String choicePet(String userId, ChoicePetDto choicePetDto){
        List<UserPetEntity> userPets = findUserPetInfo(userId);
        if(userPets.size() > 0){
            for(UserPetEntity userPet: userPets){
                userPet.setLastChoice(0);
            }
        }
        try{
            Optional<UserPetEntity> userPet = userPetRepository.findById(choicePetDto.getIdx());
            if(userPet.isPresent()){
                userPet.get().setLastChoice(1);
                userPetRepository.save(userPet.get());
            }else{
                return "fail:Pet not found";
            }
        }catch (Exception e){
            System.out.println(e);
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
    public Map<String, Object> isHasPet(String userId){
        try {
            Map<String, Object> resultMap = new HashMap<>();
            List<UserPetEntity> userPets = findUserPetInfo(userId);
            Optional<UserEntity> user = userRepository.findById(userId);
            List<Object> lst = new ArrayList<>();
            for(UserPetEntity userPet: userPets){
                Map<String,Object> petDetails = new HashMap<>();
                petDetails.put("idx",userPet.getIdx());
                petDetails.put("petId",userPet.getPetId());
                petDetails.put("natureId",userPet.getNatureId());
                petDetails.put("nickname",userPet.getPetName());
                petDetails.put("maxFriendShip",userPet.getMaxFriendship());
                petDetails.put("currentFriendShip",userPet.getCurrentFriendship());
                petDetails.put("runWayCount",userPet.getRunWayCount());
                petDetails.put("lastChoice",userPet.getLastChoice());
                lst.add(petDetails);
            }
            resultMap.put("result",lst);

            Map<String,Object> userDetails = new HashMap<>();
            userDetails.put("nickname",user.get().getNickname());
            userDetails.put("userId",user.get().getEmail());
            userDetails.put("profileUrl",user.get().getImageUrl());
            resultMap.put("userInfo",userDetails);
            return resultMap;
        }catch(NullPointerException e){
            return null;
        }
    }
    @Transactional
    public Map<String,Object> getAllPetInfo(){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<AllPetEntity> list = findAllPetInfo();
            if(list.size() > 0){
                resultMap.put("result", "success");
                resultMap.put("list", list);
            }
            else{
                resultMap.put("result", "success:nothing");
                resultMap.put("list", Collections.emptyList());
            }
        }catch(NullPointerException e){
            resultMap.put("result", "fail:Unexpected error");
            resultMap.put("list", null);
        }
        return resultMap;
    }
    public List<AllPetEntity> findAllPetInfo(){return allPetRepository.findAll();}
    public List<UserPetEntity> findUserPetInfo(String email){
        return userPetRepository.findAllByUserIdEmail(email);
    }
}