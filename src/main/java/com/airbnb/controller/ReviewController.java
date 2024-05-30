package com.airbnb.controller;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    //Add Review
    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReview(
            @PathVariable long propertyId,
            @RequestBody Review review,
            @AuthenticationPrincipal PropertyUser user){


        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        Property property = opProperty.get();
        Review r = reviewRepository.findReviewByUser(property, user);
        if(r!=null){
            return new ResponseEntity<>("You have already added a review for this property",HttpStatus.FORBIDDEN);
        }

        review.setProperty(property);
        review.setPropertyUser(user);
        reviewRepository.save(review);
        return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
    }

    //Fetch All review given by user
    @GetMapping("/userReviews")
    public ResponseEntity<List<Review>> getUserReviews(@AuthenticationPrincipal PropertyUser propertyUser){

        List<Review> reviews = reviewRepository.findByPropertyUser(propertyUser);
        return new ResponseEntity<>(reviews,HttpStatus.OK);

    }

    //Edit Review
    @PostMapping("/edit-review/{propertyId}")
    public ResponseEntity<?> editReview(@PathVariable long propertyId, @RequestBody ReviewDto dto, @AuthenticationPrincipal PropertyUser user) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        Property pro = property.get();
        Review reviewByUser = reviewRepository.findReviewByUser(pro, user);
        if (reviewByUser == null) {
            return new ResponseEntity<>("Review doesn't exist", HttpStatus.FORBIDDEN);
        }

        reviewByUser.setContent(dto.getContent());
        Review updatedReview = reviewRepository.save(reviewByUser);

        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    //Delete Review
    @PostMapping("/del-review/{propertyId}")
    public ResponseEntity<String> deleteReview(@PathVariable long propertyId,@AuthenticationPrincipal PropertyUser user){
        Optional<Property> property = propertyRepository.findById(propertyId);
        Property pro = property.get();
        Review reviewByUser = reviewRepository.findReviewByUser(pro, user);
        if(reviewByUser==null){
            return new ResponseEntity<>("Review doesn't exist",HttpStatus.FORBIDDEN);
        }
        long userId = reviewByUser.getId();
        reviewRepository.deleteById(userId);
        return new ResponseEntity<>("Review deleted", HttpStatus.OK);

    }
}
