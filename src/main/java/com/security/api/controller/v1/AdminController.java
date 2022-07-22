package com.security.api.controller.v1;


import com.security.api.advice.exception.CUserNotFoundException;
import com.security.api.entity.Admin;
import com.security.api.model.response.CommonResult;
import com.security.api.model.response.ListResult;
import com.security.api.model.response.SingleResult;
import com.security.api.repo.AdminJpaRepo;
import com.security.api.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"3. Admin"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1")
public class AdminController {
    
    private final AdminJpaRepo adminJpaRepo;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다.")
    @GetMapping(value = "/admins")
    public ListResult<Admin> findAllAdmin() {
        // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다
        return responseService.getListResult(adminJpaRepo.findAll());

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "AdminId로 회원을 조회한다.")
    @GetMapping(value = "/admin/{msrl}")
    public SingleResult<Admin> findAdminById(@ApiParam(value = "회원ID", required = true) @PathVariable long msrl,
                                           @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) throws Exception {
        // SecurityContext 에서 인증받은 회원의 정보를 얻어온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(adminJpaRepo.findByUid(id).orElseThrow(Exception::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다.")
    @PutMapping(value = "/admin")
    public SingleResult<Admin> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam Long msrl,
            @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
        Admin admin = Admin.builder()
                .msrl(msrl)
                .name(name)
                .build();
        return responseService.getSingleResult(adminJpaRepo.save(admin));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "AdminId로 회원정보를 삭제한다.")
    @DeleteMapping(value = "/admin/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable Long msrl) {
        adminJpaRepo.deleteById(msrl);
        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }
}
