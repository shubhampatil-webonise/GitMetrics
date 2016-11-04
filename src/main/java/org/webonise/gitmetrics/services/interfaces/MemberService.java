package org.webonise.gitmetrics.services.interfaces;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface MemberService {
    String getMembers() throws IOException;
}
