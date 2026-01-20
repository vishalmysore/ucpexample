package io.github.vishalmysore.service;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import io.github.vishalmysore.ucp.annotation.UCPCapability;
import io.github.vishalmysore.ucp.domain.SimpleUCPResult;
import io.github.vishalmysore.ucp.domain.UCPResult;
import org.springframework.stereotype.Service;

@Agent(groupName = "favoriteCar", groupDescription = "Handles favorite car related actions")
@Service
public class FavoriteCar {

    @Action(description = "Get the favorite car of a person")

    public UCPResult getFavoriteCar(String personName) {


        if ("Alice".equalsIgnoreCase(personName)) {
            return  new SimpleUCPResult("Tesla Model S","Alice's favorite car is Tesla Model S");
        } else if ("Bob".equalsIgnoreCase(personName)) {
            return  new SimpleUCPResult("Ford Mustang","Bob's favorite car is Ford Mustang");
        } else if ("Vishal".equalsIgnoreCase(personName)) {
            return new SimpleUCPResult("BMW M3","Vishal's favorite car is BMW M3");
        } else {
            return new SimpleUCPResult("Unknown","I don't know the favorite car of " + personName);
        }
    }
}
