package com.example.fyp.Repository;

import com.example.fyp.Model.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, Integer> {
    Optional<QRCode> findByQrCodeString(String QRCode);
}
