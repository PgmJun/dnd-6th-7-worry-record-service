package dnd.project.dnd6th7worryrecordservice.controller;

import dnd.project.dnd6th7worryrecordservice.aws.S3Uploader;
import dnd.project.dnd6th7worryrecordservice.dto.UserRequestDto;
import dnd.project.dnd6th7worryrecordservice.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("user")
@RestController
public class UserController {
    private final UserServiceImpl userService;
    private final S3Uploader s3Uploader;



    @PostMapping("register")
    @ApiOperation(value = "유저 회원가입", notes = "유저를 등록한다.")
    public void addUser(@ModelAttribute UserRequestDto userRequestDto, HttpServletResponse response) throws IOException {
        String imgUrl = s3Uploader.uploadFile(userRequestDto.getImg(), "userImage");
        System.out.println("imgUrl = " + imgUrl);
        if (imgUrl == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "업로드된 파일이 없거나 잘못된 파일입니다.");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            userService.join(userRequestDto, imgUrl);
        }
    }

}
